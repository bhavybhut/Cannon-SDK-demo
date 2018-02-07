package edsdk.api;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.win32.StdCallLibrary;

import edsdk.api.commands.GetPropertyCommand;
import edsdk.api.commands.GetPropertyDescCommand;
import edsdk.api.commands.SetPropertyCommand;

import edsdk.bindings.EdSdkLibrary;
import edsdk.bindings.EdSdkLibrary.EdsBaseRef;
import edsdk.bindings.EdSdkLibrary.EdsCameraListRef;
import edsdk.bindings.EdSdkLibrary.EdsCameraRef;
import edsdk.bindings.EdSdkLibrary.EdsObjectEventHandler;

import edsdk.utils.CanonConstants.EdsError;
import edsdk.utils.CanonConstants.EdsObjectEvent;
import edsdk.utils.CanonConstants.EdsTv;

import edsdk.utils.CanonUtils;

public class CanonCamera implements EdsObjectEventHandler {

    public static final Map<String, Integer> options = new LinkedHashMap<String, Integer>();
    public static final String edsdkDllLoc;

    static {
        URL url = null;

        try {
            url = CanonCamera.class.getProtectionDomain().getCodeSource().getLocation();
        }
        catch ( final Exception e ) {
            url = null;
        }
        if ( url == null ) {
            try {
                url = CanonCamera.class.getResource( CanonCamera.class.getSimpleName() +
                                                     ".class" );
                url = new URL( url.getPath().substring( 0, url.getPath().indexOf( '!' ) ) );
            }
            catch ( final Exception e ) {
                url = null;
            }
        }
        if ( url != null ) {
            try {
            	// handle unc paths (pre java7 :/ )
            	URI uri = url.toURI(); 
            	if( uri.getAuthority() != null && uri.getAuthority().length() > 0 ){
            		uri = new URL("file://" + url.toString().substring(5)).toURI();
            	}
                final File file = new File( uri );
                final String dir = file.getParentFile().getPath();
                System.setProperty( "jna.library.path", dir );
                //System.out.println("jna.library.path: "+dir);
            }
            catch ( final Exception e ) {
                e.printStackTrace();
            }
        }
        CanonCamera.options.put( Library.OPTION_CALLING_CONVENTION, StdCallLibrary.STDCALL_CONVENTION );

        String arch = System.getProperty( "os.arch" );
        if ( arch == null ) {
            arch = System.getProperty( "com.ibm.vm.bitmode" );
        }

        if ( arch != null && arch.endsWith( "64" ) ) {
            edsdkDllLoc = "C://Program Files (x86)/EOSCount ActiveX control/EDSDK_64.dll";
        } else {
            edsdkDllLoc = "C://Program Files (x86)/EOSCount ActiveX control/EDSDK.dll";
        }
        System.err.println( "Java Architecture: " + arch +
                            " - Using EDSDK DLL: " + CanonCamera.edsdkDllLoc );
    }

    public static final EdSdkLibrary EDSDK = (EdSdkLibrary) Native.loadLibrary( CanonCamera.edsdkDllLoc, EdSdkLibrary.class, CanonCamera.options );

    private static final User32 lib = User32.INSTANCE;
    static{
    	System.setProperty( "jna.predictable_field_order","true");
    }

    private static ConcurrentLinkedQueue<CanonCommand<?>> queue = new ConcurrentLinkedQueue<CanonCommand<?>>();

    private static ArrayList<EdsObjectEventHandler> objectEventHandlers = new ArrayList<EdsObjectEventHandler>( 10 );

    private static Thread dispatcherThread;
    static {
            CanonCamera.dispatcherThread = new Thread() {

            @Override
            public void run() {
                CanonCamera.dispatchMessages();
            }
        };
        CanonCamera.dispatcherThread.start();

        // people are sloppy! 
        // so we add a shutdown hook to close camera connections
        Runtime.getRuntime().addShutdownHook( new Thread() {

            @Override
            public void run() {
                CanonCamera.close();
            }
        } );
    }
    
    /////////////////////////////////////////////
    // From here on it's instance variables

    private EdsCameraRef edsCamera = null;

    private String errorMessage = "No Errors Yet";
    private EdsError errorCode = EdsError.EDS_ERR_OK;
    
    public void addObjectEventHandler( final EdsObjectEventHandler handler ) {
        CanonCamera.objectEventHandlers.add( handler );
    }

    /**
     * Removes an object event handler
     */
    public void removeObjectEventHandler( final EdsObjectEventHandler handler ) {
        CanonCamera.objectEventHandlers.remove( handler );
    }


    
    @Override
    public NativeLong apply( final NativeLong inEvent, final EdsBaseRef inRef,
                             final Pointer inContext ) {
        /*
         * final EdsObjectEvent event = EdsObjectEvent.enumOfValue(
         * inEvent.intValue() );
         * System.out.println( "Event " + event.value() + ": " + event.name() +
         * " - " + event.description() + ", " + inContext );
         */

        for ( final EdsObjectEventHandler handler : CanonCamera.objectEventHandlers ) {
            handler.apply( inEvent, inRef, inContext );
        }

        return new NativeLong( 0 );
    }

    private static void dispatchMessages() {
        // Do some initializing
        final EdsError err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsInitializeSDK() );
        if ( err != EdsError.EDS_ERR_OK ) {
            System.err.println( "EDSDK failed to initialize, most likely you won't be able to speak to your camera (ERROR: " +
                                err.description() + " )" );
        }

        final MSG msg = new MSG();

        CanonCommand<?> cmd = null;

        while ( !Thread.currentThread().isInterrupted() ) {
            // do we have a new message? 
            final boolean hasMessage = CanonCamera.lib.PeekMessage( msg, null, 0, 0, 1 ); // peek and remove
            if ( hasMessage ) {
                CanonCamera.lib.TranslateMessage( msg );
                CanonCamera.lib.DispatchMessage( msg );
            }

            // is there a command we're currently working on? 
            if ( cmd != null ) {
                if ( cmd.finished() ) {
                    //System.out.println( "Command finished" );
                    // great!
                    cmd.camera.removeObjectEventHandler( cmd );
                    cmd = null;
                }
            }

            // are we free to do new work, and is there even new work to be done? 
            if ( !CanonCamera.queue.isEmpty() && cmd == null ) {
                cmd = CanonCamera.queue.poll();
                /*
                 * System.out.println( "\nReceived new command, processing " +
                 * cmd.getClass().getCanonicalName().substring(
                 * cmd.getClass().getPackage().getName().length() + 1 ) );
                 */
                if ( ! ( cmd instanceof OpenSessionCommand ) ) {
                    cmd.camera.addObjectEventHandler( cmd );
                }
                cmd.run();
                cmd.ran();
            }

            try {
                Thread.sleep( 1 );
            }
            catch ( final InterruptedException e ) {
                System.out.println( "\nInterrupt received in CanonCamera, stopping..." );
                Thread.currentThread().interrupt(); // restore interrupted status
                break;
            }
        }

        CanonCamera.EDSDK.EdsTerminateSDK();
        System.out.println( "EDSDK Dispatcher thread says bye!" );
    }

    
    public CanonCamera() {}

    public boolean openSession() {
        final Boolean result = executeNow( new OpenSessionCommand() );
        return result != null && result;
    }

    public boolean closeSession() {
        final Boolean result = executeNow( new CloseSessionCommand() );
        return result != null && result;
    }
    
    public EdsCameraRef getEdsCamera() {
        return edsCamera;
    }
    
    public <T extends CanonCommand<?>> T execute( final T cmd ) {
        cmd.setCamera( this );
        CanonCamera.queue.add( cmd );
        return cmd;
    }

    public <T> T executeNow( final CanonCommand<T> cmd ) {
        if ( CanonCamera.dispatcherThread != null &&
             CanonCamera.dispatcherThread.isAlive() ) {
            return execute( cmd ).get();
        }
        return null;
    }

    public boolean setError( final EdsError result, final String message ) {
        errorCode = result;
        errorMessage = message + " (error " + errorCode.value() + ": " +
                       errorCode.name() + " - " + errorCode.description() + ")";

        System.err.println( errorMessage );

        return false;
    }

    public EdsError getLastError() {
        return errorCode;
    }

    public String getLastErrorMessage() {
        return errorMessage;
    }

    
    public static void close() {
        if ( CanonCamera.dispatcherThread != null &&
             CanonCamera.dispatcherThread.isAlive() ) {
            CanonCamera.dispatcherThread.interrupt();

            try {
                CanonCamera.dispatcherThread.join();
            }
            catch ( final InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

    private class OpenSessionCommand extends CanonCommand<Boolean> {

        @Override
        public void run() {
            setResult( connect() );
        }

        private boolean connect() {
            EdsError err = EdsError.EDS_ERR_OK;
            
            final EdsCameraListRef.ByReference listRef = new EdsCameraListRef.ByReference();
            final EdsCameraRef.ByReference cameraRef = new EdsCameraRef.ByReference();
            
            try {
                err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetCameraList( listRef ) );
                if ( err != EdsError.EDS_ERR_OK ) {
                    throw new Exception("Camera failed to initialize");
                }

                final NativeLongByReference outRef = new NativeLongByReference();
                err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetChildCount( listRef.getValue(), outRef ) );
                if ( err != EdsError.EDS_ERR_OK ) {
                    throw new Exception( "Number of attached cameras couldn't be read" );
                }

                final long numCams = outRef.getValue().longValue();
                if ( numCams <= 0 ) {
                    err = EdsError.EDS_ERR_DEVICE_NOT_FOUND;
                    throw new Exception( "No cameras found. Have you tried turning it off and on again?" );
                }

                err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetChildAtIndex( listRef.getValue(), new NativeLong( 0 ), cameraRef ) );
                if ( err != EdsError.EDS_ERR_OK ) {
                    throw new Exception( "Access to camera failed" );
                }

                err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsSetObjectEventHandler( cameraRef.getValue(), new NativeLong( EdsObjectEvent.kEdsObjectEvent_All.value() ), CanonCamera.this, new Pointer( 0 ) ) );
                if ( err != EdsError.EDS_ERR_OK ) {
                    throw new Exception( "Callback handler couldn't be added" );
                }

                err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsOpenSession( cameraRef.getValue() ) );
                if ( err != EdsError.EDS_ERR_OK ) {
                    throw new Exception( "Couldn't open camera session" );
                }
                
                CanonCamera.this.edsCamera = cameraRef.getValue();
            } catch (Exception e) {
                CanonUtils.release( cameraRef );
                setError( err, e.getMessage() );
            } finally {
                CanonUtils.release( listRef );
            }

            return err == EdsError.EDS_ERR_OK;
        }

    }

    private class CloseSessionCommand extends CanonCommand<Boolean> {

        @Override
        public void run() {
            setResult( close() );
        }

        private boolean close() {
            //System.out.println( "closing session" );
            final EdsError err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsCloseSession( edsCamera ) );
            CanonUtils.release( edsCamera );

            if ( err != EdsError.EDS_ERR_OK ) {
                return setError( err, "Couldn't close camera session" );
            }

            return true;
        }
    }

    
    public EdsTv[] getAvailableShutterSpeeds() {
        return executeNow( new GetPropertyDescCommand.ShutterSpeed() );
    }

    public GetPropertyDescCommand.ShutterSpeed getAvailableShutterSpeedsAsync() {
        return execute( new GetPropertyDescCommand.ShutterSpeed() );
    }

    
    public EdsTv getShutterSpeed() {
        return executeNow( new GetPropertyCommand.ShutterSpeed() );
    }

    public GetPropertyCommand.ShutterSpeed getShutterSpeedAsync() {
        return execute( new GetPropertyCommand.ShutterSpeed() );
    }

    public EdsTv[] getAvailableShutterCounts() {
        return executeNow( new GetPropertyDescCommand.ShutterCounter() );
    }
    
    public EdsTv getShutterCounter() {
        return executeNow( new GetPropertyCommand.ShutterCounter() );
    }

    public Long getBatteryLevel() {
        return executeNow( new GetPropertyCommand.BatteryLevel() );
    }

    public GetPropertyCommand.BatteryLevel getBatteryLevelAsync() {
        return execute( new GetPropertyCommand.BatteryLevel() );
    }

    public Boolean setShutterSpeed( final EdsTv value ) {
        return executeNow( new SetPropertyCommand.ShutterSpeed( value ) );
    }

    public SetPropertyCommand.ShutterSpeed setShutterSpeedAsync( final EdsTv value ) {
        return execute( new SetPropertyCommand.ShutterSpeed( value ) );
    }

}
