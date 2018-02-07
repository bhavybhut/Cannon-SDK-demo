package edsdk.utils;

import java.io.File;
import java.nio.IntBuffer;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.NativeLongByReference;

import edsdk.api.CanonCamera;
import edsdk.bindings.EdSdkLibrary.EdsBaseRef;
import edsdk.bindings.EdSdkLibrary.EdsCameraRef;
import edsdk.bindings.EdSdkLibrary.EdsDirectoryItemRef;
import edsdk.bindings.EdSdkLibrary.EdsEvfImageRef;
import edsdk.bindings.EdSdkLibrary.EdsStreamRef;
import edsdk.bindings.EdsCapacity;
import edsdk.bindings.EdsDirectoryItemInfo;
import edsdk.bindings.EdsFocusInfo;
import edsdk.bindings.EdsPictureStyleDesc;
import edsdk.bindings.EdsPoint;
import edsdk.bindings.EdsPropertyDesc;
import edsdk.bindings.EdsRational;
import edsdk.bindings.EdsRect;
import edsdk.bindings.EdsTime;
import edsdk.utils.CanonConstants.DescriptiveEnum;
import edsdk.utils.CanonConstants.EdsAEMode;
import edsdk.utils.CanonConstants.EdsAFMode;
import edsdk.utils.CanonConstants.EdsAv;
import edsdk.utils.CanonConstants.EdsColorSpace;
import edsdk.utils.CanonConstants.EdsCustomFunction;
import edsdk.utils.CanonConstants.EdsDataType;
import edsdk.utils.CanonConstants.EdsDriveMode;
import edsdk.utils.CanonConstants.EdsError;
import edsdk.utils.CanonConstants.EdsEvfAFMode;
import edsdk.utils.CanonConstants.EdsEvfOutputDevice;
import edsdk.utils.CanonConstants.EdsExposureCompensation;
import edsdk.utils.CanonConstants.EdsISOSpeed;
import edsdk.utils.CanonConstants.EdsImageQuality;
import edsdk.utils.CanonConstants.EdsMeteringMode;
import edsdk.utils.CanonConstants.EdsPictureStyle;
import edsdk.utils.CanonConstants.EdsPropertyID;
import edsdk.utils.CanonConstants.EdsTv;
import edsdk.utils.CanonConstants.EdsWhiteBalance;

public class CanonUtils {

    public static final int classIntField( final Class<?> klass,
                                           final String fieldName ) {
        Throwable t = null;
        try {
            final int value = klass.getField( fieldName ).getInt( null );
            return value;
        }
        catch ( final IllegalArgumentException e ) {
            t = e;
        }
        catch ( final IllegalAccessException e ) {
            t = e;
        }
        catch ( final NoSuchFieldException e ) {
            t = e;
        }
        catch ( final SecurityException e ) {
            t = e;
        }
        throw new IllegalArgumentException( klass.getCanonicalName() +
                                            " does not contain field " +
                                            fieldName, t );
    }

    /**
     * Finds the filename for a directory item
     * 
     * @param directoryItem The item you want to download
     * @return Either null, or the filename of the item
     */
    public static EdsDirectoryItemInfo getDirectoryItemInfo( final EdsDirectoryItemRef directoryItem ) {
        EdsError err = EdsError.EDS_ERR_OK;
        final EdsDirectoryItemInfo dirItemInfo = new EdsDirectoryItemInfo();

        try {
            err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetDirectoryItemInfo( directoryItem, dirItemInfo ) );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return err == EdsError.EDS_ERR_OK ? dirItemInfo : null;
    }

    public static File download( final EdsDirectoryItemRef directoryItem,
                                 File destination,
                                 final boolean appendFileExtension ) {
        EdsError err = EdsError.EDS_ERR_OK;
        final EdsStreamRef.ByReference stream = new EdsStreamRef.ByReference();
        final EdsDirectoryItemInfo dirItemInfo = new EdsDirectoryItemInfo();

        boolean success = false;

        //final long timeStart = System.currentTimeMillis();

        try {
            err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetDirectoryItemInfo( directoryItem, dirItemInfo ) );
            if ( err == EdsError.EDS_ERR_OK ) {
                if ( destination == null ) {
                    destination = new File( System.getProperty( "java.io.tmpdir" ) );
                }
                if ( destination.isDirectory() ) {
                    destination = new File( destination, Native.toString( dirItemInfo.szFileName ) );
                } else if ( appendFileExtension ) {
                    final String sourceFileName = Native.toString( dirItemInfo.szFileName );
                    final int i = sourceFileName.lastIndexOf( "." );
                    if ( i > 0 ) {
                        final String extension = sourceFileName.substring( i );
                        if ( !destination.getName().toLowerCase().endsWith( extension ) ) {
                            destination = new File( destination.getPath() +
                                                    extension );
                        }
                    }
                }

                if ( destination.getParentFile() != null ) {
                    destination.getParentFile().mkdirs();
                }

                /*
                 * System.out.println( "Downloading image " +
                 * Native.toString( dirItemInfo.szFileName ) +
                 * " to " + destination.getCanonicalPath() );
                 */

                // TODO: see if using an EdsCreateMemoryStream would be faster and whether the image could be read directly without saving to file first - see: http://stackoverflow.com/questions/1083446/canon-edsdk-memorystream-image
                //err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsCreateFileStream( Native.toByteArray( destination.getCanonicalPath() ), EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value(), EdsAccess.kEdsAccess_ReadWrite.value(), stream ) );
            }

            if ( err == EdsError.EDS_ERR_OK ) {
            }

            if ( err == EdsError.EDS_ERR_OK ) {
                success = true;
            }

            if ( stream != null ) {
                CanonCamera.EDSDK.EdsRelease( stream.getValue() );
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return success ? destination : null;
    }

    public static EdsError setPropertyData( final EdsBaseRef ref,
                                            final EdsPropertyID property,
                                            final DescriptiveEnum<? extends Number> value ) {
        return CanonUtils.setPropertyDataAdvanced( ref, property, 0, value.value().longValue() );
    }

    public static EdsError setPropertyData( final EdsBaseRef ref,
                                            final EdsPropertyID property,
                                            final long param,
                                            final DescriptiveEnum<? extends Number> value ) {
        return CanonUtils.setPropertyDataAdvanced( ref, property, param, value.value().longValue() );
    }
    public static EdsError setPropertyData( final EdsBaseRef ref,
                                            final EdsPropertyID property,
                                            final long value ) {
        return CanonUtils.setPropertyDataAdvanced( ref, property, 0, value );
    }


    public static EdsError setPropertyData( final EdsBaseRef ref,
                                            final EdsPropertyID property,
                                            final long param, final long value ) {
        return CanonUtils.setPropertyDataAdvanced( ref, property, param, value );
    }

    public static EdsError setPropertyData( final EdsBaseRef ref,
                                            final EdsPropertyID property,
                                            final long param, final int size,
                                            final Pointer data ) {
        return CanonUtils.toEdsError( CanonCamera.EDSDK.EdsSetPropertyData( ref, new NativeLong( property.value() ), new NativeLong( param ), new NativeLong( size ), data ) );
    }

    public static EdsError setPropertyDataAdvanced( final EdsBaseRef ref,
                                                    final EdsPropertyID property,
                                                    final Object value ) {
        return CanonUtils.setPropertyDataAdvanced( ref, property, 0, value );
    }

    public static EdsError setPropertyDataAdvanced( final EdsBaseRef ref,
                                                    final EdsPropertyID property,
                                                    final long param,
                                                    final Object value ) throws IllegalStateException {

        final EdsDataType type = CanonUtils.getPropertyType( ref, property, param );

        final Pointer pointer;
        final int size;

        switch ( type ) {
            case kEdsDataType_String: { //EdsChar[]
                final String string = (String) value;
                size = string.length() + 1;
                pointer = new Memory( size );
                pointer.setString( 0, string );
                break;
            }
            case kEdsDataType_Int8: //EdsInt8
            case kEdsDataType_UInt8: { //EdsUInt8
                size = 1;
                pointer = new Memory( size );
                pointer.setByte( 0, (Byte) value );
                break;
            }
            case kEdsDataType_Int16: //EdsInt16
            case kEdsDataType_UInt16: { //EdsUInt16
                size = 2;
                pointer = new Memory( size );
                pointer.setShort( 0, (Short) value );
                break;
            }
            case kEdsDataType_Int32: //EdsInt32
            case kEdsDataType_UInt32: { //EdsUInt32
                size = 4;
                pointer = new Memory( size );
                pointer.setNativeLong( 0, new NativeLong( (Long) value ) );
                break;
            }
            case kEdsDataType_Int64: //EdsInt64
            case kEdsDataType_UInt64: { //EdsUInt64
                size = 8;
                pointer = new Memory( size );
                pointer.setLong( 0, (Long) value );
                break;
            }
            case kEdsDataType_Float: { //EdsFloat
                size = 4;
                pointer = new Memory( size );
                pointer.setFloat( 0, (Float) value );
                break;
            }
            case kEdsDataType_Double: { //EdsDouble
                size = 8;
                pointer = new Memory( size );
                pointer.setDouble( 0, (Double) value );
                break;
            }
            case kEdsDataType_ByteBlock: { //Byte Block // TODO: According to API, is either EdsInt8[] or EdsUInt32[], but perhaps former is a typo or an old value
                final int[] array = (int[]) value;
                size = 4 * array.length;
                pointer = new Memory( size );
                pointer.write( 0, array, 0, array.length );
                break;
            }
            case kEdsDataType_Rational: //EdsRational
            case kEdsDataType_Point: //EdsPoint
            case kEdsDataType_Rect: //EdsRect
            case kEdsDataType_Time: //EdsTime
            case kEdsDataType_FocusInfo: //EdsFocusInfo
            case kEdsDataType_PictureStyleDesc: { //EdsPictureStyleDesc
                final Structure structure = (Structure) value;
                structure.write();
                pointer = structure.getPointer();
                size = structure.size();
                break;
            }
            case kEdsDataType_Int8_Array: //EdsInt8[]
            case kEdsDataType_UInt8_Array: { //EdsUInt8[]
                final byte[] array = (byte[]) value;
                size = array.length;
                pointer = new Memory( size );
                pointer.write( 0, array, 0, array.length );
                break;
            }
            case kEdsDataType_Int16_Array: //EdsInt16[]
            case kEdsDataType_UInt16_Array: { //EdsUInt16[]
                final short[] array = (short[]) value;
                size = 2 * array.length;
                pointer = new Memory( size );
                pointer.write( 0, array, 0, array.length );
                break;
            }
            case kEdsDataType_Int32_Array: //EdsInt32[]
            case kEdsDataType_UInt32_Array: { //EdsUInt32[]
                final int[] array = (int[]) value;
                size = 4 * array.length;
                pointer = new Memory( size );
                pointer.write( 0, array, 0, array.length );
                break;
            }
            case kEdsDataType_Bool: 
            	//EdsBool // TODO: implement
            case kEdsDataType_Bool_Array: //EdsBool[] // TODO: implement
            case kEdsDataType_Rational_Array: //EdsRational[] // TODO: implement
            case kEdsDataType_Unknown: //Unknown
            default:
                throw new IllegalStateException( type.description() + " (" +
                                                 type.name() +
                                                 ") is not currently supported by GetPropertyCommand" );
        }
        return CanonUtils.setPropertyData( ref, property, param, size, pointer );
    }

    public static Long getPropertyData( final EdsBaseRef ref,
                                        final EdsPropertyID property ) {
        return CanonUtils.getPropertyDataAdvanced( ref, property, 0 );
    }

    public static Long getPropertyData( final EdsBaseRef ref,
                                        final EdsPropertyID property,
                                        final long param ) {
        return CanonUtils.getPropertyDataAdvanced( ref, property, param );
    }

    public static EdsError getPropertyData( final EdsBaseRef ref,
                                            final EdsPropertyID property,
                                            final long param, final long size,
                                            final Pointer data ) {
        return CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetPropertyData( ref, new NativeLong( property.value() ), new NativeLong( param ), new NativeLong( size ), data ) );
    }

    public static <T> T getPropertyDataAdvanced( final EdsBaseRef ref,
                                                 final EdsPropertyID property ) {
        return CanonUtils.getPropertyDataAdvanced( ref, property, 0 );
    }

    public static <T> T getPropertyDataAdvanced( final EdsBaseRef ref,
                                                 final EdsPropertyID property,
                                                 final long param ) throws IllegalArgumentException, IllegalStateException {

        final int size = (int) CanonUtils.getPropertySize( ref, property, param );
        final EdsDataType type = CanonUtils.getPropertyType( ref, property, param );

        final Memory memory = new Memory( size > 0 ? size : 1 );

        final EdsError err = CanonUtils.getPropertyData( ref, property, param, size, memory );
        if ( err == EdsError.EDS_ERR_OK ) {
            switch ( type ) {
                case kEdsDataType_Unknown: //Unknown
                    return null;
                case kEdsDataType_String: //EdsChar[]
                    return (T) memory.getString( 0 );
                case kEdsDataType_Int8: //EdsInt8
                case kEdsDataType_UInt8: //EdsUInt8
                    return (T) Byte.valueOf( memory.getByte( 0 ) );
                case kEdsDataType_Int16: //EdsInt16
                case kEdsDataType_UInt16: //EdsUInt16
                    return (T) Short.valueOf( memory.getShort( 0 ) );
                case kEdsDataType_Int32: //EdsInt32
                case kEdsDataType_UInt32: //EdsUInt32
                    return (T) Long.valueOf( memory.getNativeLong( 0 ).longValue() );
                case kEdsDataType_Int64: //EdsInt64
                case kEdsDataType_UInt64: //EdsUInt64
                    return (T) Long.valueOf( memory.getLong( 0 ) );
                case kEdsDataType_Float: //EdsFloat
                    return (T) Float.valueOf( memory.getFloat( 0 ) );
                case kEdsDataType_Double: //EdsDouble
                    return (T) Double.valueOf( memory.getDouble( 0 ) );
                case kEdsDataType_ByteBlock: //Byte Block // TODO: According to API, is either EdsInt8[] or EdsUInt32[], but perhaps former is a typo or an old value
                    return (T) memory.getIntArray( 0, size / 4 );
                case kEdsDataType_Rational: //EdsRational
                    return (T) new EdsRational( memory );
                case kEdsDataType_Point: //EdsPoint
                    return (T) new EdsPoint( memory );
                case kEdsDataType_Rect: //EdsRect
                    return (T) new EdsRect( memory );
                case kEdsDataType_Time: //EdsTime
                    return (T) new EdsTime( memory );
                case kEdsDataType_FocusInfo: //EdsFocusInfo
                    return (T) new EdsFocusInfo( memory );
                case kEdsDataType_PictureStyleDesc: //EdsPictureStyleDesc
                    return (T) new EdsPictureStyleDesc( memory );
                case kEdsDataType_Int8_Array: //EdsInt8[]
                case kEdsDataType_UInt8_Array: //EdsUInt8[]
                    return (T) memory.getByteArray( 0, size );
                case kEdsDataType_Int16_Array: //EdsInt16[]
                case kEdsDataType_UInt16_Array: //EdsUInt16[]
                    return (T) memory.getShortArray( 0, size / 2 );
                case kEdsDataType_Int32_Array: //EdsInt32[]
                case kEdsDataType_UInt32_Array: //EdsUInt32[]
                    return (T) memory.getIntArray( 0, size / 4 );
                case kEdsDataType_Bool: //EdsBool // TODO: implement
                case kEdsDataType_Bool_Array: //EdsBool[] // TODO: implement
                case kEdsDataType_Rational_Array: //EdsRational[] // TODO: implement
                default:
                    throw new IllegalStateException( type.description() + " (" +
                                                     type.name() +
                                                     ") is not currently supported by GetPropertyCommand" );
            }
        }

        throw new IllegalArgumentException( "An error occurred while getting " +
                                            property.name() + " data (error " +
                                            err.value() + ": " + err.name() +
                                            " - " + err.description() + ")" );
    }

    /**
     * see {@link CanonUtils#getPropertyType(EdsBaseRef, EdsPropertyID, long)}
     * @param ref
     * @param property
     * @return
     */
    public static EdsDataType getPropertyType( final EdsBaseRef ref,
                                               final EdsPropertyID property ) {
        return CanonUtils.getPropertyType( ref, property, 0 );
    }

    /**
     * Returns the data type of a specified property
     * 
     * @param ref Reference to the camera
     * @param property Property id
     * @param param Name of the parameter
     * @return The data type, or null if the parameter isn't supported, or unknown if something else goes wrong.  
     */
    public static EdsDataType getPropertyType( final EdsBaseRef ref,
                                               final EdsPropertyID property,
                                               final long param ) {
        final int bufferSize = 1;
        final IntBuffer type = IntBuffer.allocate( bufferSize );
        final NativeLongByReference number = new NativeLongByReference( new NativeLong( bufferSize ) );
        final EdsError err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetPropertySize( ref, new NativeLong( property.value() ), new NativeLong( param ), type, number ) );
        if ( err == EdsError.EDS_ERR_OK ) {
            final EdsDataType edsDataType = EdsDataType.enumOfValue( type.get( 0 ) );
            if ( edsDataType != null ) {
                //System.out.println( " > property type = " + edsDataType.value() + " : " + edsDataType.name() + " : " + edsDataType.description() );
                return edsDataType;
            }
        }
        else if( err == EdsError.EDS_ERR_NOT_SUPPORTED ){
        	return null; 
        }
        
        return EdsDataType.kEdsDataType_Unknown;
    }

    /**
     * Get the size of a property
     * @param ref Reference to the camera/image/liveview object
     * @param property the property id
     * @return Size in bytes
     */
    public static long getPropertySize( final EdsBaseRef ref,
                                        final EdsPropertyID property ) {
        return CanonUtils.getPropertySize( ref, property, 0 );
    }

    /**
     * Get the size of a property
     * @param ref Reference to the camera/image/liveview object
     * @param property the property id
     * @param param
     * @return Size in bytes
     */
    public static long getPropertySize( final EdsBaseRef ref,
                                        final EdsPropertyID property,
                                        final long param ) {
        final int bufferSize = 1;
        final IntBuffer type = IntBuffer.allocate( bufferSize );
        final NativeLongByReference number = new NativeLongByReference( new NativeLong( bufferSize ) );
        final EdsError err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetPropertySize( ref, new NativeLong( property.value() ), new NativeLong( param ), type, number ) );
        if ( err == EdsError.EDS_ERR_OK ) {
            //System.out.println( "> property size = " + number.getValue().longValue() );
            return number.getValue().longValue();
        }
        return -1;
    }

    public static final DescriptiveEnum<?>[] getPropertyDesc( final EdsCameraRef camera,
                                                              final EdsPropertyID property ) throws IllegalArgumentException, IllegalStateException {

        /*
         * System.out.println( "Getting available property values for " +
         * property.description() + " (" + property.name() +
         * ")" );
         */

        final EdsPropertyDesc propertyDesc = CanonUtils.getPropertyDesc( (EdsBaseRef) camera, property );

        if ( propertyDesc.numElements.intValue() > 0 ) {
            /*
             * System.out.println( "Number of elements: " +
             * propertyDesc.numElements );
             */

            final NativeLong[] propDesc = propertyDesc.propDesc;
            final DescriptiveEnum<?>[] properties = new DescriptiveEnum<?>[propertyDesc.numElements.intValue()];
            for ( int i = 0; i < propertyDesc.numElements.intValue(); i++ ) {
                DescriptiveEnum<?> de = null;
                switch ( property ) {
                    case kEdsPropID_DriveMode:
                        de = EdsDriveMode.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_ISOSpeed:
                        de = EdsISOSpeed.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_MeteringMode:
                        de = EdsMeteringMode.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_AFMode:
                        de = EdsAFMode.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_Av:
                        de = EdsAv.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_Tv:
                        de = EdsTv.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_ShutterCounter:
                        de = EdsTv.enumOfValue( propDesc[i].intValue() );
                        break;                    
                    case kEdsPropID_ExposureCompensation:
                        de = EdsExposureCompensation.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_AEMode:
                        de = EdsAEMode.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_ImageQuality:
                        de = EdsImageQuality.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_WhiteBalance:
                        de = EdsWhiteBalance.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_ColorSpace:
                        de = EdsColorSpace.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_PictureStyle:
                        de = EdsPictureStyle.enumOfValue( propDesc[i].intValue() );
                        break;
                    // Doesn't seem possible to query available output devices
                    //                    case kEdsPropID_Evf_OutputDevice:
                    //                        de = EdsEvfOutputDevice.enumOfValue( propDesc[i].intValue() );
                    //                        break;
                    case kEdsPropID_Evf_WhiteBalance:
                        de = EdsWhiteBalance.enumOfValue( propDesc[i].intValue() );
                        break;
                    case kEdsPropID_Evf_AFMode:
                        de = EdsEvfAFMode.enumOfValue( propDesc[i].intValue() );
                        break;
                    default:
                        throw new IllegalArgumentException( "Property '" +
                                                            property.name() +
                                                            "' is not supported." );
                }
                if ( de == null ) {
                    throw new IllegalStateException( "Could not find " +
                                                     property.name() +
                                                     " enum with value of: " +
                                                     propDesc[i].intValue() );
                } else {
                    //System.out.println( e.name() + " ( " + e.value() + " ) " + e.description() );
                }
                properties[i] = de;
            }
            //System.out.println( "DONE!\n" );

            return properties;
        }
        return null;
    }

    public static EdsPropertyDesc getPropertyDesc( final EdsBaseRef ref,
                                                   final EdsPropertyID property ) throws IllegalArgumentException {
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc();
        final EdsError err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsGetPropertyDesc( ref, new NativeLong( property.value() ), propertyDesc ) );
        if ( err == EdsError.EDS_ERR_OK ) {
            //System.out.println( "> available values = " + propertyDesc.numElements );
            return propertyDesc;
        }
        throw new IllegalArgumentException( "An error occurred while getting detailed " +
                                            property.name() +
                                            " data (error " +
                                            err.value() +
                                            ": " +
                                            err.name() +
                                            " - " + err.description() + ")" );
    }

    public static EdsError setCapacity( final EdsCameraRef ref ) {
        return CanonUtils.setCapacity( ref, Integer.MAX_VALUE );
    }

    public static EdsError setCapacity( final EdsCameraRef ref,
                                        final int capacity ) {
        final EdsCapacity.ByValue edsCapacity = new EdsCapacity.ByValue();
        edsCapacity.bytesPerSector = new NativeLong( 512 );
        edsCapacity.numberOfFreeClusters = new NativeLong( capacity /
                                                           edsCapacity.bytesPerSector.intValue() );
        edsCapacity.reset = 1;
        return CanonUtils.toEdsError( CanonCamera.EDSDK.EdsSetCapacity( ref, edsCapacity ) );
    }

    public static boolean isMirrorLockupEnabled( final EdsCameraRef camera ) {
        try {
            return 1l == CanonUtils.getPropertyData( camera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value() );
        }
        catch ( final IllegalArgumentException e ) {
            System.err.println( "Could not check if mirror lockup enabled: " +
                                e.getMessage() );
        }
        return false;
    }

    public static boolean beginLiveView( final EdsCameraRef camera ) {
        EdsError err = EdsError.EDS_ERR_OK;

        NativeLongByReference number = new NativeLongByReference( new NativeLong( 1 ) );
        Pointer data = number.getPointer();
        err = CanonUtils.setPropertyData( camera, EdsPropertyID.kEdsPropID_Evf_Mode, 0, NativeLong.SIZE, data );
        if ( err != EdsError.EDS_ERR_OK ) {
            System.err.println( "Could not start live view (set image mode) (error " +
                                err.value() +
                                ": " +
                                err.name() +
                                " - " +
                                err.description() + ")" );
            return false;
        }

        number = new NativeLongByReference( new NativeLong( EdsEvfOutputDevice.kEdsEvfOutputDevice_PC.value() ) );
        data = number.getPointer();
        err = CanonUtils.setPropertyData( camera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, 0, NativeLong.SIZE, data );
        if ( err != EdsError.EDS_ERR_OK ) {
            System.err.println( "Could not start live view (set output device) (error " +
                                err.value() +
                                ": " +
                                err.name() +
                                " - " +
                                err.description() + ")" );
            return false;
        }

        return true;
    }

    public static boolean endLiveView( final EdsCameraRef camera ) {
        EdsError err = EdsError.EDS_ERR_OK;

        NativeLongByReference number = new NativeLongByReference( new NativeLong( EdsEvfOutputDevice.kEdsEvfOutputDevice_TFT.value() ) );
        Pointer data = number.getPointer();
        err = CanonUtils.setPropertyData( camera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, 0, NativeLong.SIZE, data );
        if ( err != EdsError.EDS_ERR_OK ) {
            /*
             * System.err.println( "Could not end live view (error " +
             * err.value() + ": " + err.name() + " - " +
             * err.description() + ")" );
             */
            return false;
        }

        //TODO: decide whether skip deactivating the live view system. Canon's EOS Utility leaves it enabled, so should consider leaving it enabled as well.
        number = new NativeLongByReference( new NativeLong( 0 ) );
        data = number.getPointer();
        err = CanonUtils.setPropertyData( camera, EdsPropertyID.kEdsPropID_Evf_Mode, 0, NativeLong.SIZE, data );
        if ( err != EdsError.EDS_ERR_OK ) {
            /*
             * System.err.println( "Could not end live view (error " +
             * err.value() + ": " + err.name() + " - " +
             * err.description() + ")" );
             */
            return false;
        }

        return true;
    }

    public static boolean isLiveViewEnabled( final EdsCameraRef camera,
                                             final boolean checkLiveViewActive ) {
        try {
            if ( checkLiveViewActive ) {
                final EdsBaseRef.ByReference[] references = CanonUtils.getLiveViewImageReference( camera );
                if ( references != null ) {
                    CanonUtils.release( references );
                    return true;
                }
                return false;
            }
            return 1 == CanonUtils.getPropertyData( camera, EdsPropertyID.kEdsPropID_Evf_Mode );
        }
        catch ( final IllegalArgumentException e ) {
            System.err.println( "Could not check live view status: " +
                                e.getMessage() );
        }
        return false;
    }

   
    public static EdsBaseRef.ByReference[] getLiveViewImageReference( final EdsCameraRef camera ) {
        EdsError err = EdsError.EDS_ERR_OK;

        final EdsStreamRef.ByReference streamRef = new EdsStreamRef.ByReference();
        final EdsEvfImageRef.ByReference imageRef = new EdsEvfImageRef.ByReference();

        // Create memory stream.
        err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsCreateMemoryStream( new NativeLong( 0 ), streamRef ) );
        if ( err != EdsError.EDS_ERR_OK ) {
            System.err.println( "Failed to download live view image, memory stream could not be created (error " +
                                err.value() +
                                ": " +
                                err.name() +
                                " - " +
                                err.description() + ")" );
            CanonUtils.release( imageRef, streamRef );
            return null;
        }

        err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsCreateEvfImageRef( new EdsStreamRef( streamRef.getPointer().getPointer( 0 ) ), imageRef ) );
        if ( err != EdsError.EDS_ERR_OK ) {
            System.err.println( "Failed to download live view image, image ref could not be created (error " +
                                err.value() +
                                ": " +
                                err.name() +
                                " - " +
                                err.description() + ")" );
            CanonUtils.release( imageRef, streamRef );
            return null;
        }

        // Now try to follow the guidelines from 
        // http://tech.groups.yahoo.com/group/CanonSDK/message/1225
        // instead of what the edsdk example has to offer! 

        // Download live view image data.
        err = CanonUtils.toEdsError( CanonCamera.EDSDK.EdsDownloadEvfImage( camera, imageRef.getValue() ) );
        if ( err != EdsError.EDS_ERR_OK ) {
            /*
             * System.err.println( "Failed to download live view image (error "
             * +
             * err.value() + ": " + err.name() + " - " +
             * err.description() + ")" );
             */
            CanonUtils.release( imageRef, streamRef );
            return null;
        }

        return new EdsBaseRef.ByReference[] { imageRef, streamRef };
    }

    public static EdsError toEdsError( final NativeLong value ) {
        return CanonUtils.toEdsError( value.intValue() );
    }
    public static EdsError toEdsError( final long value ) {
        return CanonUtils.toEdsError( value );
    }

    public static EdsError toEdsError( final int value ) {
        final EdsError error = EdsError.enumOfValue( value );
        if ( error != null ) {
            return error;
        }
        return EdsError.EDS_ERR_UNEXPECTED_EXCEPTION;
    }

    public static void release( final EdsBaseRef.ByReference ... objects ) {
        for ( final EdsBaseRef.ByReference obj : objects ) {
            if ( obj != null ) {
                CanonUtils.release( obj.getValue() );
            }
        }
    }

    public static void release( final EdsBaseRef ... objects ) {
        for ( final EdsBaseRef obj : objects ) {
            if ( obj != null ) {
                CanonCamera.EDSDK.EdsRelease( obj );
            }
        }
    }
}
