package edsdk.api.commands;

import edsdk.api.CanonCommand;
import edsdk.bindings.EdSdkLibrary.EdsBaseRef;

import edsdk.utils.CanonConstants.DescriptiveEnum;
import edsdk.utils.CanonConstants.EdsDataType;
import edsdk.utils.CanonConstants.EdsError;
import edsdk.utils.CanonConstants.EdsPropertyID;
import edsdk.utils.CanonConstants.EdsTv;

import edsdk.utils.CanonUtils;
public abstract class SetPropertyCommand<T> extends CanonCommand<Boolean> {

    private final EdsPropertyID property;
    private final long param;
    private final T value;
    private final Class<T> klass;
    private final boolean isLiveViewCommand;
    private final int liveViewRetryCount = 2;
    private final int busyRetryCount = 500;

    public SetPropertyCommand( final EdsPropertyID property, final T value ) {
        this( property, 0, value, false );
    }

    public SetPropertyCommand( final EdsPropertyID property, final long param,
                               final T value ) {
        this( property, param, value, false );
    }

    public SetPropertyCommand( final EdsPropertyID property, final T value,
                               final boolean isLiveViewCommand ) {
        this( property, 0, value, isLiveViewCommand );
    }

    @SuppressWarnings( "unchecked" )
    public SetPropertyCommand( final EdsPropertyID property, final long param,
                               final T value, final boolean isLiveViewCommand ) {
        this.property = property;
        this.param = param;
        this.value = value;
        if ( value != null ) {
            klass = (Class<T>) value.getClass();
        } else {
            klass = null;
        }
        this.isLiveViewCommand = isLiveViewCommand;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void run() {
        if ( value == null ) {
            throw new IllegalStateException( "Cannot set a null value!" );
        }

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

            EdsError err = EdsError.EDS_ERR_DEVICE_BUSY;
            int retries = 0;
            while ( retries < busyRetryCount &&
                    err == EdsError.EDS_ERR_DEVICE_BUSY ) {
                //TODO: it would be better to use the actual error from CanonUtils.getPropertyType(), but in testing a valid value was always returned if something was supported
                err = EdsError.EDS_ERR_NOT_SUPPORTED;
                switch ( type ) {
                    case kEdsDataType_Int32: //EdsInt32
                    case kEdsDataType_UInt32: { //EdsUInt32
                        final long longValue;

                        if ( Boolean.class.isAssignableFrom( klass ) ) {
                            // Boolean
                            longValue = (Boolean) value ? 1l : 0l;
                        } else if ( DescriptiveEnum.class.isAssignableFrom( klass ) ) {
                            // DescriptiveEnum
                            longValue = ( (DescriptiveEnum<? extends Number>) value ).value().longValue();
                        } else {
                            // Long
                            longValue = (Long) value;
                        }

                        err = CanonUtils.setPropertyData( baseRef, property, param, longValue );
                        break;
                    }
                    case kEdsDataType_String: //EdsChar[]
                    case kEdsDataType_Point: //EdsPoint
                    case kEdsDataType_Rect: //EdsRect
                    case kEdsDataType_Time: //EdsTime
                    case kEdsDataType_FocusInfo: //EdsFocusInfo
                    case kEdsDataType_PictureStyleDesc: { //EdsPictureStyleDesc
                        err = CanonUtils.setPropertyDataAdvanced( baseRef, property, param, value );
                        break;
                    }
                    case kEdsDataType_ByteBlock: //EdsUInt32[]
                    case kEdsDataType_Int32_Array: //EdsInt32[]
                    case kEdsDataType_UInt32_Array: { //EdsUInt32[]
                        final int[] array;

                        if ( DescriptiveEnum[].class.isAssignableFrom( klass ) ) {
                            // DescriptiveEnum[]
                            final DescriptiveEnum<? extends Number>[] valueArray = (DescriptiveEnum<? extends Number>[]) value;
                            array = new int[valueArray.length];
                            for ( int i = 0; i < valueArray.length; i++ ) {
                                array[i] = valueArray[i].value().intValue();
                            }
                        } else if ( DescriptiveEnum.class.isAssignableFrom( klass ) ) {
                            // DescriptiveEnum
                            array = new int[] { ( (DescriptiveEnum<? extends Number>) value ).value().intValue() };
                        } else {
                            // int[]
                            array = (int[]) value;
                        }

                        err = CanonUtils.setPropertyDataAdvanced( baseRef, property, param, array );
                        break;
                    }
                    default:
                        System.err.println( type.description() +
                                            " (" +
                                            type.name() +
                                            ") is not currently supported by SetPropertyCommand. Likely this camera does not support property " +
                                            property.name() +
                                            " in the current mode or at all." );

                        //                    throw new IllegalStateException( type.description() + " (" +
                        //                                                     type.name() +
                        //                                                     ") is not currently supported by SetPropertyCommand. Likely this camera does not support property " + property.name() + " in the current mode or at all." );
                }
                retries++;
                Thread.sleep( 10 );
            }
            
            if( retries > 1 ){
	            System.out.println( "Set property: " + property.name() + " - " +
	                                property.description() +
	                                ( param > 0l ? param : "" ) + " = " + value +
	                                ", result " + err.value() + ": " + err.name() +
	                                " - " + err.description() + " after " +
	                                retries + " tries" );
            }
            setResult( err == EdsError.EDS_ERR_OK );
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

    public static class Data extends SetPropertyCommand<Long> {

        public Data( final EdsPropertyID property, final long value,
                     final boolean isLiveViewCommand ) {
            super( property, value, true );
        }

        public Data( final EdsPropertyID property, final long value ) {
            super( property, value );
        }

    }

    public static class EnumData extends SetPropertyCommand<DescriptiveEnum<? extends Number>> {

        public EnumData( final EdsPropertyID property,
                         final DescriptiveEnum<? extends Number> value,
                         final boolean isLiveViewCommand ) {
            super( property, value, true );
        }

        public EnumData( final EdsPropertyID property,
                         final DescriptiveEnum<? extends Number> value ) {
            super( property, value );
        }

    }

    /*
     * Specific Property ID Commands
     */
    public static class ShutterSpeed extends SetPropertyCommand<EdsTv> {

        public ShutterSpeed( final EdsTv value ) {
            super( EdsPropertyID.kEdsPropID_Tv, value );
        }

    }
    
    public static class ShutterCounter extends SetPropertyCommand<EdsTv> {

        public ShutterCounter( final EdsTv value ) {
            super( EdsPropertyID.kEdsPropID_ShutterCounter, value );
        }

    }
}
