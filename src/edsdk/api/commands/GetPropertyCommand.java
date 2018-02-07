package edsdk.api.commands;

import java.lang.reflect.Array;

import edsdk.api.CanonCommand;
import edsdk.bindings.EdSdkLibrary.EdsBaseRef;
import edsdk.utils.CanonConstants;
import edsdk.utils.CanonConstants.DescriptiveEnum;
import edsdk.utils.CanonConstants.EdsBatteryQuality;
import edsdk.utils.CanonConstants.EdsDataType;
import edsdk.utils.CanonConstants.EdsPropertyID;
import edsdk.utils.CanonConstants.EdsTv;
import edsdk.utils.CanonUtils;

public abstract class GetPropertyCommand<T> extends CanonCommand<T> {

    private final EdsPropertyID property;
    private final long param;
    private final Class<T> klass;
    private final boolean isLiveViewCommand;
    private final int liveViewRetryCount = 2;

    public GetPropertyCommand( final EdsPropertyID property ) {
        this( property, 0, null, false );
    }

    public GetPropertyCommand( final EdsPropertyID property, final long param ) {
        this( property, param, null, false );
    }

    public GetPropertyCommand( final EdsPropertyID property,
                               final boolean isLiveViewCommand ) {
        this( property, 0, null, isLiveViewCommand );
    }

    public GetPropertyCommand( final EdsPropertyID property, final long param,
                               final boolean isLiveViewCommand ) {
        this( property, param, null, isLiveViewCommand );
    }

    public GetPropertyCommand( final EdsPropertyID property,
                               final Class<T> klass ) {
        this( property, 0, klass, false );
    }

    public GetPropertyCommand( final EdsPropertyID property, final long param,
                               final Class<T> klass ) {
        this( property, param, klass, false );
    }

    public GetPropertyCommand( final EdsPropertyID property,
                               final Class<T> klass,
                               final boolean isLiveViewCommand ) {
        this( property, 0, klass, isLiveViewCommand );
    }

    public GetPropertyCommand( final EdsPropertyID property, final long param,
                               final Class<T> klass,
                               final boolean isLiveViewCommand ) {
        this.property = property;
        this.param = param;
        this.klass = klass;
        this.isLiveViewCommand = isLiveViewCommand;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void run() {
        Throwable t = null;
        EdsBaseRef.ByReference[] references = null;
        try {
            final EdsBaseRef baseRef;
            if ( isLiveViewCommand ) {
                if ( CanonUtils.isLiveViewEnabled( camera.getEdsCamera(), false ) ) {
                    for ( int i = 0; i < liveViewRetryCount &&
                                     references == null; i++ ) {
                        if ( i > 0 ) {
                            Thread.sleep( 100 );
                        }
                        references = CanonUtils.getLiveViewImageReference( camera.getEdsCamera() );
                    }
                    if ( references != null ) {
                        baseRef = references[0].getValue();
                    } else {
                        //TODO: it may take several seconds for live view to start, so this might happen every time.. perhaps the previous should be tried for a few seconds
                        //throw new IllegalStateException( "Could not retrieve live view image reference!" );
                        System.err.println( "Could not retrieve live view image reference!" );
                        setResult( null );
                        return;
                    }
                } else {
                    //throw new IllegalStateException( "Live view is not enabled!" );
                    System.err.println( "Live view is not enabled!" );
                    setResult( null );
                    return;
                }
            } else {
                baseRef = camera.getEdsCamera();
            }

            final EdsDataType type = CanonUtils.getPropertyType( baseRef, property, param );

            T result = null;
            switch ( type ) {
                case kEdsDataType_Int32: //EdsInt32
                case kEdsDataType_UInt32: { //EdsUInt32
                    final Long data = CanonUtils.getPropertyData( baseRef, property, param );

                    if ( data != null ) {
                        if ( klass != null &&
                             Boolean.class.isAssignableFrom( klass ) ) {
                            // Boolean
                            result = (T) Boolean.valueOf( data == 1l );
                        } else if ( klass != null &&
                                    DescriptiveEnum.class.isAssignableFrom( klass ) ) {
                            // DescriptiveEnum
                            result = (T) CanonConstants.enumOfValue( (Class<? extends DescriptiveEnum<?>>) klass, data.intValue() );
                        } else {
                            // Long
                            result = (T) Long.valueOf( data );
                        }
                    }

                    break;
                }
                case kEdsDataType_String: { //EdsChar[]
                    final String data = CanonUtils.getPropertyDataAdvanced( baseRef, property, param );
                    result = (T) data;
                    break;
                }
                case kEdsDataType_ByteBlock: //EdsUInt32[]
                case kEdsDataType_Int32_Array: //EdsInt32[]
                case kEdsDataType_UInt32_Array: { //EdsUInt32[]
                    final int[] data = CanonUtils.getPropertyDataAdvanced( baseRef, property, param );

                    if ( data != null ) {
                        if ( klass != null &&
                             DescriptiveEnum[].class.isAssignableFrom( klass ) ) {
                            // DescriptiveEnum[]
                            final DescriptiveEnum<?>[] array = (DescriptiveEnum<?>[]) Array.newInstance( klass.getComponentType(), data.length );
                            for ( int i = 0; i < data.length; i++ ) {
                                array[i] = CanonConstants.enumOfValue( (Class<? extends DescriptiveEnum<?>>) klass.getComponentType(), data[i] );
                            }
                            result = (T) array;
                        } else if ( klass != null &&
                                    DescriptiveEnum.class.isAssignableFrom( klass ) ) {
                            // DescriptiveEnum
                            if ( data.length > 1 ) {
                                throw new IllegalStateException( "Only single result expected but multiple results returned!" );
                            }
                            result = (T) CanonConstants.enumOfValue( (Class<? extends DescriptiveEnum<?>>) klass, data[0] );
                        } else {
                            // int[]
                            result = (T) data;
                        }
                    }

                    break;
                }
                default:
                    System.err.println( type.description() +
                                        " (" +
                                        type.name() +
                                        ") is not currently supported by GetPropertyCommand. Likely this camera does not support property " +
                                        property.name() +
                                        " in the current mode or at all." );

                    //                    throw new IllegalStateException( type.description() + " (" +
                    //                                                     type.name() +
                    //                                                     ") is not currently supported by GetPropertyCommand. Likely this camera does not support property " + property.name() + " in the current mode or at all." );
            }

            setResult( result );
            return;
        }
        catch ( final IllegalArgumentException e ) {
            t = e;
        }
        catch ( final InterruptedException e ) {
            t = e;
        }
        finally {
            if ( references != null ) {
                CanonUtils.release( references );
            }
        }
        System.err.println( t.getMessage() );
        setResult( null );
    }

    public static class Data extends GetPropertyCommand<Long> {

        public Data( final EdsPropertyID property,
                     final boolean isLiveViewCommand ) {
            super( property, true );
        }

        public Data( final EdsPropertyID property ) {
            super( property );
        }

    }


    public static class BatteryLevel extends GetPropertyCommand<Long> {

        public BatteryLevel() {
            super( EdsPropertyID.kEdsPropID_BatteryLevel );
        }

    }

    public static class BatteryQuality extends GetPropertyCommand<EdsBatteryQuality> {

        public BatteryQuality() {
            super( EdsPropertyID.kEdsPropID_BatteryQuality, EdsBatteryQuality.class );
        }

    }

    public static class AvailableShots extends GetPropertyCommand<Long> {

        public AvailableShots() {
            super( EdsPropertyID.kEdsPropID_AvailableShots );
        }

    }

    public static class ShutterSpeed extends GetPropertyCommand<EdsTv> {

        public ShutterSpeed() {
            super( EdsPropertyID.kEdsPropID_Tv, EdsTv.class );
        }
    }
    
    public static class ShutterCounter extends GetPropertyCommand<EdsTv> {

        public ShutterCounter() {
            super( EdsPropertyID.kEdsPropID_ShutterCounter, EdsTv.class );
        }
    }

}
