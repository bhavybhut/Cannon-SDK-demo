package edsdk.api;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import edsdk.bindings.EdSdkLibrary.EdsBaseRef;
import edsdk.bindings.EdSdkLibrary.EdsCameraRef;
import edsdk.bindings.EdSdkLibrary.EdsObjectEventHandler;
import edsdk.utils.CanonUtils;
import edsdk.utils.CanonConstants.DescriptiveEnum;
import edsdk.utils.CanonConstants.EdsCameraCommand;
import edsdk.utils.CanonConstants.EdsError;
import edsdk.utils.CanonConstants.EdsObjectEvent;

public abstract class CanonCommand<T> implements EdsObjectEventHandler {

    public CanonCamera camera;
    // edsCamera can be reached also through camera.getEdsCamera(), 
    // but it's needed so much that this is a handy shortcut. 
    public EdsCameraRef edsCamera; 
    private boolean finished = false;
    private boolean waitForFinish = false;
    private boolean ran = false;
    private T result;
    private final ReentrantLock lock = new ReentrantLock();
    private ArrayList<CanonCommandListener<T>> listeners = null;

    public CanonCommand() {}

    /**
     * The camera is set by the dispatch thread automatically just before run is
     * called.
     * 
     * @param camera
     */
    public void setCamera( final CanonCamera camera ) {
        this.camera = camera;
        this.edsCamera = camera.getEdsCamera(); 
    }

    /**
     * This should be short and sweet!
     * If your command needs to wait for events
     */
    public abstract void run();

    /**
     * Sets the result
     */
    public void setResult( final T result ) {
        this.result = result;
    }

    /**
     * By default a CanonCommand will be marked as finished as soon as
     * the run method completed. If you attached listens inside run
     * and are waiting for a special event to happen before you're done
     * please call the notYetFinished() at the end of run().
     * 
     * This will tell the dispatcher that it should start forwarding event
     * messages again, and also it'll wait with the execution of further
     * commands until your command somehow calls finish() on itself to
     * let the dispatcher know that it's done.
     * <
     */
    public void notYetFinished() {
        waitForFinish = true;
    }

    /**
     * Only used in combination with notYetFinished.
     * Call this when your command's work is done (e.g. you successfully
     * shot and downloaded an image).
     * 
     * @see CanonCommand#notYetFinished()
     */
    public void finish() {
        finished = true;
        notifyListenersIfDone();
    }

    /**
     * Don't _ever_ call this, promise!
     */
    protected void ran() {
        ran = true;
        notifyListenersIfDone();
    }

    /**
     * Checks if this command finished it's work. Only useful in combination
     * with
     * finish() and notYetFinished().
     * 
     * @see CanonCommand#notYetFinished()
     * @return
     */
    public boolean finished() {
        return waitForFinish ? finished : ran;
    }
    @Override
    public NativeLong apply( final NativeLong inEvent, final EdsBaseRef inRef,
                             final Pointer inContext ) {
        return new NativeLong( apply( EdsObjectEvent.enumOfValue( inEvent.intValue() ), inRef, inContext ).value() );
    }

    public EdsError apply( final EdsObjectEvent inEvent,
                           final EdsBaseRef inRef, final Pointer inContext ) {
        return EdsError.EDS_ERR_OK;
    }

    public T get() {
        try {
            return get( 0 );
        }
        catch ( final InterruptedException e ) {
            // this shouldn't happen since get( 0 ) internally handles InterruptedException when timeout=0.
            e.printStackTrace();
        }
        return null;
    }

    public T get( final long timeout ) throws InterruptedException {
        if ( this.camera == null ) {
            System.err.println( "Attention: " + getClass() );
            System.err.println( "  This command was not yet added to a queue " );
            System.err.println( "  with CanonCamera.execute( ... )" );
            System.err.println( "  This way you might wait forever until .get() returns. " );
        }

        final long startTime = System.currentTimeMillis();
        try {
            while ( !finished() &&
                    ( timeout == 0 || System.currentTimeMillis() - startTime < timeout ) ) {
                Thread.sleep( 1 );
            }
        }
        catch ( final InterruptedException e ) {
            System.out.println( "Interrupt received by CanonCommand, stopping..." );
            Thread.currentThread().interrupt(); // restore interrupted status
            return null;
        }

        if ( finished() ) {
            return result;
        } else {
            //TODO: should we just interrupt the thread instead?
            throw new InterruptedException( "edsdkp5 - command didn't return the result in time" );
        }
    }

    /**
     * An alias for get()
     * 
     * @return
     */
    public T now() {
        return get();
    }

    /**
     * Add a done listener
     * 
     * @param listener
     */
    public void whenDone( final CanonCommandListener<T> listener ) {
        lock.lock();
        if ( finished() ) {
            listener.success( result );
        } else {
            if ( listeners == null ) {
                listeners = new ArrayList<CanonCommandListener<T>>();
            }
            listeners.add( listener );
        }
        lock.unlock();
    }

    private void notifyListenersIfDone() {
        lock.lock();
        if ( finished() && listeners != null ) {
            for ( final CanonCommandListener<T> listener : listeners ) {
                listener.success( result );
            }
            listeners = null;
        }
        lock.unlock();
    }
    
    
    
    /**
     * Sends a command to the camera
     * 
     * @return
     */
    public EdsError sendCommand( final EdsCameraCommand command,
                                 final DescriptiveEnum<? extends Number> params ) {
        return sendCommand( command, params.value().longValue() );
    }

    /**
     * Sends a command to the camera
     * 
     * @return
     */
    public EdsError sendCommand( final EdsCameraCommand command,
                                 final long params ) {
        return CanonUtils.toEdsError( CanonCamera.EDSDK.EdsSendCommand( camera.getEdsCamera(), new NativeLong( command.value() ), new NativeLong( params ) ) );
    }


}
