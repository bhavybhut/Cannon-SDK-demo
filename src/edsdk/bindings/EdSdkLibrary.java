package edsdk.bindings;

// import com.sun.jna.Native;
// import com.sun.jna.NativeLibrary;
import java.nio.IntBuffer;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface EdSdkLibrary extends StdCallLibrary {

    public static final String JNA_LIBRARY_NAME = "EdSdk";

    //public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(EdSdkLibrary.JNA_LIBRARY_NAME);
    //public static final EdSdkLibrary INSTANCE = (EdSdkLibrary)Native.loadLibrary(EdSdkLibrary.JNA_LIBRARY_NAME, EdSdkLibrary.class);
    /**
     * <i>native declaration : EDSDK\Header\EDSDKTypes.h</i><br>
     * enum values
     */
    public static interface EdsDataType {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:134</i>
        public static final int kEdsDataType_Unknown = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:135</i>
        public static final int kEdsDataType_Bool = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:136</i>
        public static final int kEdsDataType_String = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:137</i>
        public static final int kEdsDataType_Int8 = 3;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:138</i>
        public static final int kEdsDataType_UInt8 = 6;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:139</i>
        public static final int kEdsDataType_Int16 = 4;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:140</i>
        public static final int kEdsDataType_UInt16 = 7;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:141</i>
        public static final int kEdsDataType_Int32 = 8;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:142</i>
        public static final int kEdsDataType_UInt32 = 9;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:143</i>
        public static final int kEdsDataType_Int64 = 10;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:144</i>
        public static final int kEdsDataType_UInt64 = 11;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:145</i>
        public static final int kEdsDataType_Float = 12;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:146</i>
        public static final int kEdsDataType_Double = 13;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:147</i>
        public static final int kEdsDataType_ByteBlock = 14;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:148</i>
        public static final int kEdsDataType_Rational = 20;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:149</i>
        public static final int kEdsDataType_Point = 21;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:150</i>
        public static final int kEdsDataType_Rect = 22;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:151</i>
        public static final int kEdsDataType_Time = 23;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:153</i>
        public static final int kEdsDataType_Bool_Array = 30;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:154</i>
        public static final int kEdsDataType_Int8_Array = 31;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:155</i>
        public static final int kEdsDataType_Int16_Array = 32;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:156</i>
        public static final int kEdsDataType_Int32_Array = 33;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:157</i>
        public static final int kEdsDataType_UInt8_Array = 34;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:158</i>
        public static final int kEdsDataType_UInt16_Array = 35;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:159</i>
        public static final int kEdsDataType_UInt32_Array = 36;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:160</i>
        public static final int kEdsDataType_Rational_Array = 37;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:162</i>
        public static final int kEdsDataType_FocusInfo = 101;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:163</i>
        public static final int kEdsDataType_PictureStyleDesc = 102;
    };

    public static interface EdsShutterButton {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:328</i>
        public static final int kEdsCameraCommand_ShutterButton_OFF = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:329</i>
        public static final int kEdsCameraCommand_ShutterButton_Halfway = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:330</i>
        public static final int kEdsCameraCommand_ShutterButton_Completely = 3;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:331</i>
        public static final int kEdsCameraCommand_ShutterButton_Halfway_NonAF = 65537;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:332</i>
        public static final int kEdsCameraCommand_ShutterButton_Completely_NonAF = 65539;
    };


    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_SEEK_ERROR = 165;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_PARAMETER = 96;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_VolumeInfoChanged = 513;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_ShutDownTimerUpdate = 772;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_NOT_FOUND = 34;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_CMP_ID_CLIENT_COMPONENTID = 16777216;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Bracket = 1035;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSLatitudeRef = 2049;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_FN_CALL = 241;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_WhiteBalanceBracket = 1036;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INCOMPATIBLE_VERSION = 6;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_DriveMode = 1025;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_PROTECTION_VIOLATION = 9;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Copyright = 1049;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FocalLength = 1033;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_DEVICEPROP_FORMAT = 8219;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_LensName = 1037;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_DISK_FULL_ERROR = 42;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_PictureStyle = 276;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_DigitalExposure = 261;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_WAIT_TIMEOUT_ERROR = 244;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_OBJECTFORMATCODE = 8203;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_MOVIE_CROP_NG = 36105;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_LENGTH = 100;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_WhiteBalanceShift = 264;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_FolderUpdateItems = 515;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_TRANSACTIONID = 8196;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_VolumeAdded = 524;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Contrast = 265;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_READ_ERROR = 167;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_DISK_ERROR = 136;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FlashMode = 1044;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_SELECTION_UNAVAILABLE = 11;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int EDS_MAX_NAME = 256;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_RESERVED_MASK = 16711680;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_CMP_ID_HLSDK_COMPONENTID = 50331648;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_JobStatusChanged = 770;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_Histogram = 1290;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_ALREADY_OPEN = 162;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ISSPECIFIC_MASK = -2147483648;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_MyMenu = 14;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemRemoved = 517;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ICCProfile = 259;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_TOO_MANY_OPEN = 33;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_PictureStyleCaption = 512;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_UNEXPECTED_EXCEPTION = 8;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STI_DEVICE_RELEASE_ERROR = 227;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_SILENCE_NG = 36101;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_BatteryQuality = 16;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_DATA_CORRUPT = 45;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_OutputDevice = 1280;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_OwnerName = 4;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Sharpness = 268;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_SORT_FN = 102;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_DepthOfField = 1051;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_ENUM_NA = 240;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STI_UNKNOWN_ERROR = 224;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_POINTER = 98;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_WBCoeffs = 770;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_CANNOT_MAKE_OBJECT = 41220;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSLongitude = 2052;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_INVALID = 130;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_USB_DEVICE_UNLOCK_ERROR = 209;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Tv = 1030;
    public static final int kEdsPropID_ShutterCounter = 34;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_SPECIFICATION_OF_DESTINATION_UNSUPPORTED = 8224;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraStatusCommand_EnterDirectTransfer = 2;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FirmwareVersion = 7;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DIR_IO_ERROR = 65;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_CAPTURE_ALREADY_TERMINATED = 8216;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_ID = 243;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_HDDirectoryStructure = 32;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_ALREADY_EXISTS = 43;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_MEMORY_FULL = 132;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSLongitudeRef = 2051;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_HANDLE = 97;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Artist = 1048;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_NOT_SUPPORTED = 7;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_DoClickWBEvf = 260;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_INDEX = 99;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemContentChanged = 519;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_PARENTOBJECT = 8218;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_NO_DISK = 135;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_ImagePosition = 1291;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_DateTime = 6;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraStatusCommand_UILock = 0;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_PERMISSION_ERROR = 169;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropertyEvent_PropertyChanged = 257;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_NOT_RELEASED = 141;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_MakerName = 5;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemCreated = 516;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_NO_VALID_OBJECTINFO = 8213;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_ExtendShutDownTimer = 1;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Av = 1029;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_CARD_NG = 36103;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_DIAL_CHANGED = 138;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_BUSY = 129;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_LENS_COVER_CLOSE = 40966;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ProductName = 2;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_BulbEnd = 3;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_BatteryLevel = 8;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Orientation = 258;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FlashCompensation = 1032;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_CF_GATE_CHANGED = 137;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int EDS_TRANSFER_BLOCK_SIZE = 512;
    /// <i>native declaration : EDSDK\Header\EDSDK.h</i>
    public static final int oldif = 0;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSTimeStamp = 2055;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICEPROP_NOT_SUPPORTED = 8202;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_CFn = 9;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ColorMatrix = 275;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_NoiseReduction = 1041;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_COMM_PORT_IS_IN_USE = 192;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_LAST_GENERIC_ERROR_PLUS_ONE = 245;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSVersionID = 2048;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_ColorTemperature = 1283;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSStatus = 2057;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_END_OF_STREAM = 172;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_WRITE_ERROR = 40;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DIR_NOT_FOUND = 64;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ToneCurve = 270;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_PressShutterButton = 4;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_ZoomRect = 1345;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_OK = 0;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_AtCapture_Flag = -2147483648;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSDateStamp = 2077;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DIR_ENTRY_EXISTS = 67;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_SEEK_ERROR = 37;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_PROPERTIES_NOT_LOADED = 83;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_SESSION_NOT_OPEN = 8195;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_SaveTo = 11;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_CARD_PROTECT_NG = 36104;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ExposureCompensation = 1031;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_CurrentFolder = 13;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraStatusCommand_UIUnLock = 1;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_INTERNAL_ERROR = 133;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_FORMAT_UNRECOGNIZED = 44;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_InternalError = 774;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_COMPONENTID_MASK = 2130706432;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DIR_ENTRY_NOT_FOUND = 66;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_BulbStart = 2;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ColorTemperature = 263;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_BAD_OPTIONS = 171;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_VolumeUpdateItems = 514;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_COMM_BUFFER_FULL = 195;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_COMM_USB_BUS_ERR = 196;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ColorSpace = 269;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_AFMode = 1028;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_AF_NG = 36097;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_BulbExposureTime = 784;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_NOT_INSTALLED = 139;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_WhiteBalance = 1282;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_INVALID_PARAMETER = 134;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_PhotoEffect = 271;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_DepthOfFieldPreview = 1284;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_Mode = 1281;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_FocusAid = 1289;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FlashOn = 1042;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ISOBracket = 1040;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_SPECIFICATION_BY_FORMAT_UNSUPPORTED = 8212;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_EFCompensation = 1054;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_PictureStyleDesc = 277;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_DoEvfAf = 258;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropertyEvent_PropertyDescChanged = 258;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ISOSpeed = 1026;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_PROPERTIES_MISMATCH = 81;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_WRITE_ERROR = 168;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INTERNAL_ERROR = 2;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_MEM_FREE_FAILED = 4;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_OPEN_ERROR = 35;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_JpegQuality = 257;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_CoordinateSystem = 1344;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STI_INTERNAL_ERROR = 225;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_AvailableShots = 1034;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STI_DEVICE_CREATE_ERROR = 226;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_CODE_FORMAT = 8214;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_AfResult = 777;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_All = 768;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERRORID_MASK = 65535;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_RedEye = 1043;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_UNIMPLEMENTED = 1;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_OPERATION_CANCELLED = 5;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_ZoomPosition = 1288;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemRequestTransfer = 520;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int NULL = 0;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TRANSACTION_CANCELLED = 8223;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_Zoom = 1287;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_All = 512;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FilterEffect = 272;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_CLOSE_ERROR = 164;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_STRAGEID = 8200;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_MEM_ALLOC_FAILED = 3;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FocusInfo = 260;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_STAY_AWAKE = 140;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_NOT_OPEN = 161;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropertyEvent_All = 256;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemCancelTransferDT = 522;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG = 36099;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_HANDLE_NOT_FOUND = 242;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_Shutdown = 769;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_DEVICEPROP_VALUE = 8220;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_HistogramStatus = 1292;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSAltitudeRef = 2053;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_COMM_DEVICE_INCOMPATIBLE = 194;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_NOT_LAUNCHED = 228;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ImageQuality = 256;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_TELL_ERROR = 38;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_SESSION_ALREADY_OPEN = 8222;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_NOT_FOUND = 128;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_IO_ERROR = 160;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INVALID_FN_POINTER = 101;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int FALSE = 0;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_OPERATION_REFUSED = 40965;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSMapDatum = 2066;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_VolumeRemoved = 525;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_SELF_TEST_FAILED = 8209;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_WhiteBalance = 262;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSAltitude = 2054;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_COMM_DISCONNECTED = 193;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemRequestTransferDT = 521;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_USB_DEVICE_LOCK_ERROR = 208;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_CMP_ID_LLSDK_COMPONENTID = 33554432;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSSatellites = 2056;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_AEMode = 1024;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_DriveLensEvf = 259;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraStatusCommand_ExitDirectTransfer = 3;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ToningEffect = 273;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DEVICE_EMERGENCY = 131;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsCameraCommand_TakePicture = 0;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_TELL_ERROR = 166;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_LensStatus = 1046;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ParameterSet = 274;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_STROBO_CHARGE_NG = 36106;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_CaptureError = 773;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Unknown = 65535;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_PROPERTIES_UNAVAILABLE = 80;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_AEBracket = 1038;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_PARTIAL_DELETION = 8210;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_RESERVED = 36098;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_COULDNT_BEGIN_THREAD = 170;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_READ_ERROR = 39;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ColorTone = 267;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_GPSLatitude = 2050;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_MISSING_SUBCOMPONENT = 10;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ColorSaturation = 266;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_MeteringMode = 1027;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_NAMING_NA = 46;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Linear = 768;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_IO_ERROR = 32;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_CurrentStorage = 12;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_FEBracket = 1039;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_PERMISSION_ERROR = 41;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_ClickWBPoint = 769;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsStateEvent_WillSoonShutDown = 771;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_SENSOR_CLEANING_NG = 36100;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int TRUE = 1;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_FILE_CLOSE_ERROR = 36;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_INCOMPLETE_TRANSFER = 8199;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_TAKE_PICTURE_NO_CARD_NG = 36102;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_LOW_BATTERY = 41217;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_UNKNOWN_COMMAND = 40961;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_UNKNOWN_VENDOR_CODE = 8215;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_STREAM_OPEN_ERROR = 163;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsObjectEvent_DirItemInfoChanged = 518;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_DIR_NOT_EMPTY = 68;
    /// <i>native declaration : EDSDK\Header\EDSDKErrors.h</i>
    public static final int EDS_ERR_OBJECT_NOTREADY = 41218;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_BodyIDEx = 21;
    /// <i>native declaration : EDSDK\Header\EDSDKTypes.h</i>
    public static final int kEdsPropID_Evf_AFMode = 1294;

    public static class EdsDirectoryItemRef extends EdsBaseRef {

        public EdsDirectoryItemRef() {
            super();
        }

        public EdsDirectoryItemRef( final Pointer address ) {
            super( address );
        }

        public static class ByReference extends EdsBaseRef.ByReference {

            @Override
            public EdsDirectoryItemRef getValue() {
                final Pointer p = getPointer().getPointer( 0 );
                if ( p == null ) {
                    return null;
                }
                return new EdsDirectoryItemRef( p );
            }
        }
    }
    public static interface EdsEvfOutputDevice {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:1004</i>
        public static final int kEdsEvfOutputDevice_TFT = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:1005</i>
        public static final int kEdsEvfOutputDevice_PC = 2;
    };

    public static class EdsEvfImageRef extends EdsBaseRef {

        public EdsEvfImageRef() {
            super();
        }

        public EdsEvfImageRef( final Pointer address ) {
            super( address );
        }

        public static class ByReference extends EdsBaseRef.ByReference {

            @Override
            public EdsEvfImageRef getValue() {
                final Pointer p = getPointer().getPointer( 0 );
                if ( p == null ) {
                    return null;
                }
                return new EdsEvfImageRef( p );
            }
        }
    }
    public static interface EdsFileCreateDisposition {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:578</i>
        public static final int kEdsFileCreateDisposition_CreateNew = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:579</i>
        public static final int kEdsFileCreateDisposition_CreateAlways = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:580</i>
        public static final int kEdsFileCreateDisposition_OpenExisting = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:581</i>
        public static final int kEdsFileCreateDisposition_OpenAlways = 3;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:582</i>
        public static final int kEdsFileCreateDisposition_TruncateExsisting = 4;
    };
    
    public static interface EdsAEMode {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:963</i>
        public static final int kEdsAEMode_Program = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:964</i>
        public static final int kEdsAEMode_Tv = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:965</i>
        public static final int kEdsAEMode_Av = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:966</i>
        public static final int kEdsAEMode_Manual = 3;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:967</i>
        public static final int kEdsAEMode_Bulb = 4;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:968</i>
        public static final int kEdsAEMode_A_DEP = 5;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:969</i>
        public static final int kEdsAEMode_DEP = 6;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:970</i>
        public static final int kEdsAEMode_Custom = 7;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:971</i>
        public static final int kEdsAEMode_Lock = 8;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:972</i>
        public static final int kEdsAEMode_Green = 9;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:973</i>
        public static final int kEdsAEMode_NightPortrait = 10;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:974</i>
        public static final int kEdsAEMode_Sports = 11;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:975</i>
        public static final int kEdsAEMode_Portrait = 12;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:976</i>
        public static final int kEdsAEMode_Landscape = 13;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:977</i>
        public static final int kEdsAEMode_Closeup = 14;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:978</i>
        public static final int kEdsAEMode_FlashOff = 15;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:979</i>
        public static final int kEdsAEMode_CreativeAuto = 19;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:980</i>
        public static final int kEdsAEMode_Movie = 20;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:981</i>
        public static final int kEdsAEMode_PhotoInMovie = 21;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:982</i>
        public static final int kEdsAEMode_Unknown = -1;
    };
    
    public static interface EdsColorSpace {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:920</i>
        public static final int kEdsColorSpace_sRGB = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:921</i>
        public static final int kEdsColorSpace_AdobeRGB = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:922</i>
        public static final int kEdsColorSpace_Unknown = -1;
    };

    public static interface EdsWhiteBalance {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:840</i>
        public static final int kEdsWhiteBalance_Auto = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:841</i>
        public static final int kEdsWhiteBalance_Daylight = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:842</i>
        public static final int kEdsWhiteBalance_Cloudy = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:843</i>
        public static final int kEdsWhiteBalance_Tangsten = 3;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:844</i>
        public static final int kEdsWhiteBalance_Fluorescent = 4;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:845</i>
        public static final int kEdsWhiteBalance_Strobe = 5;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:846</i>
        public static final int kEdsWhiteBalance_WhitePaper = 6;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:847</i>
        public static final int kEdsWhiteBalance_Shade = 8;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:848</i>
        public static final int kEdsWhiteBalance_ColorTemp = 9;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:849</i>
        public static final int kEdsWhiteBalance_PCSet1 = 10;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:850</i>
        public static final int kEdsWhiteBalance_PCSet2 = 11;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:851</i>
        public static final int kEdsWhiteBalance_PCSet3 = 12;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:852</i>
        public static final int kEdsWhiteBalance_WhitePaper2 = 15;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:853</i>
        public static final int kEdsWhiteBalance_WhitePaper3 = 16;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:854</i>
        public static final int kEdsWhiteBalance_WhitePaper4 = 18;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:855</i>
        public static final int kEdsWhiteBalance_WhitePaper5 = 19;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:856</i>
        public static final int kEdsWhiteBalance_PCSet4 = 20;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:857</i>
        public static final int kEdsWhiteBalance_PCSet5 = 21;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:858</i>
        public static final int kEdsWhiteBalance_Click = -1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:859</i>
        public static final int kEdsWhiteBalance_Pasted = -2;
    };
    
    public static interface EdsImageQuality {

        /**
         * Jpeg Only<br>
         * Jpeg Large<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:638</i>
         */
        public static final int EdsImageQuality_LJ = 1113871;
        /**
         * Jpeg Middle1<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:639</i>
         */
        public static final int EdsImageQuality_M1J = 84999951;
        /**
         * Jpeg Middle2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:640</i>
         */
        public static final int EdsImageQuality_M2J = 101777167;
        /**
         * Jpeg Small<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:641</i>
         */
        public static final int EdsImageQuality_SJ = 34668303;
        /**
         * Jpeg Large Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:642</i>
         */
        public static final int EdsImageQuality_LJF = 1310479;
        /**
         * Jpeg Large Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:643</i>
         */
        public static final int EdsImageQuality_LJN = 1244943;
        /**
         * Jpeg Middle Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:644</i>
         */
        public static final int EdsImageQuality_MJF = 18087695;
        /**
         * Jpeg Middle Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:645</i>
         */
        public static final int EdsImageQuality_MJN = 18022159;
        /**
         * Jpeg Small Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:646</i>
         */
        public static final int EdsImageQuality_SJF = 34864911;
        /**
         * Jpeg Small Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:647</i>
         */
        public static final int EdsImageQuality_SJN = 34799375;
        /**
         * Jpeg Small1 Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:648</i>
         */
        public static final int EdsImageQuality_S1JF = 236191503;
        /**
         * Jpeg Small1 Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:649</i>
         */
        public static final int EdsImageQuality_S1JN = 236125967;
        /**
         * Jpeg Small2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:650</i>
         */
        public static final int EdsImageQuality_S2JF = 252968719;
        /**
         * Jpeg Small3<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:651</i>
         */
        public static final int EdsImageQuality_S3JF = 269745935;
        /**
         * RAW + Jpeg<br>
         * RAW<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:654</i>
         */
        public static final int EdsImageQuality_LR = 6618895;
        /**
         * RAW + Jpeg Large Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:655</i>
         */
        public static final int EdsImageQuality_LRLJF = 6553619;
        /**
         * RAW + Jpeg Large Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:656</i>
         */
        public static final int EdsImageQuality_LRLJN = 6553618;
        /**
         * RAW + Jpeg Middle Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:657</i>
         */
        public static final int EdsImageQuality_LRMJF = 6553875;
        /**
         * RAW + Jpeg Middle Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:658</i>
         */
        public static final int EdsImageQuality_LRMJN = 6553874;
        /**
         * RAW + Jpeg Small Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:659</i>
         */
        public static final int EdsImageQuality_LRSJF = 6554131;
        /**
         * RAW + Jpeg Small Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:660</i>
         */
        public static final int EdsImageQuality_LRSJN = 6554130;
        /**
         * RAW + Jpeg Small1 Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:661</i>
         */
        public static final int EdsImageQuality_LRS1JF = 6557203;
        /**
         * RAW + Jpeg Small1 Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:662</i>
         */
        public static final int EdsImageQuality_LRS1JN = 6557202;
        /**
         * RAW + Jpeg Small2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:663</i>
         */
        public static final int EdsImageQuality_LRS2JF = 6557459;
        /**
         * RAW + Jpeg Small3<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:664</i>
         */
        public static final int EdsImageQuality_LRS3JF = 6557715;
        /**
         * RAW + Jpeg Large<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:666</i>
         */
        public static final int EdsImageQuality_LRLJ = 6553616;
        /**
         * RAW + Jpeg Middle1<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:667</i>
         */
        public static final int EdsImageQuality_LRM1J = 6554896;
        /**
         * RAW + Jpeg Middle2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:668</i>
         */
        public static final int EdsImageQuality_LRM2J = 6555152;
        /**
         * RAW + Jpeg Small<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:669</i>
         */
        public static final int EdsImageQuality_LRSJ = 6554128;
        /**
         * MRAW(SRAW1) + Jpeg<br>
         * MRAW(SRAW1)<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:672</i>
         */
        public static final int EdsImageQuality_MR = 23396111;
        /**
         * MRAW(SRAW1) + Jpeg Large Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:673</i>
         */
        public static final int EdsImageQuality_MRLJF = 23330835;
        /**
         * MRAW(SRAW1) + Jpeg Large Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:674</i>
         */
        public static final int EdsImageQuality_MRLJN = 23330834;
        /**
         * MRAW(SRAW1) + Jpeg Middle Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:675</i>
         */
        public static final int EdsImageQuality_MRMJF = 23331091;
        /**
         * MRAW(SRAW1) + Jpeg Middle Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:676</i>
         */
        public static final int EdsImageQuality_MRMJN = 23331090;
        /**
         * MRAW(SRAW1) + Jpeg Small Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:677</i>
         */
        public static final int EdsImageQuality_MRSJF = 23331347;
        /**
         * MRAW(SRAW1) + Jpeg Small Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:678</i>
         */
        public static final int EdsImageQuality_MRSJN = 23331346;
        /**
         * MRAW(SRAW1) + Jpeg Small1 Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:679</i>
         */
        public static final int EdsImageQuality_MRS1JF = 23334419;
        /**
         * MRAW(SRAW1) + Jpeg Small1 Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:680</i>
         */
        public static final int EdsImageQuality_MRS1JN = 23334418;
        /**
         * MRAW(SRAW1) + Jpeg Small2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:681</i>
         */
        public static final int EdsImageQuality_MRS2JF = 23334675;
        /**
         * MRAW(SRAW1) + Jpeg Small3<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:682</i>
         */
        public static final int EdsImageQuality_MRS3JF = 23334931;
        /**
         * MRAW(SRAW1) + Jpeg Large<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:684</i>
         */
        public static final int EdsImageQuality_MRLJ = 23330832;
        /**
         * MRAW(SRAW1) + Jpeg Middle1<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:685</i>
         */
        public static final int EdsImageQuality_MRM1J = 23332112;
        /**
         * MRAW(SRAW1) + Jpeg Middle2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:686</i>
         */
        public static final int EdsImageQuality_MRM2J = 23332368;
        /**
         * MRAW(SRAW1) + Jpeg Small<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:687</i>
         */
        public static final int EdsImageQuality_MRSJ = 23331344;
        /**
         * SRAW(SRAW2) + Jpeg<br>
         * SRAW(SRAW2)<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:690</i>
         */
        public static final int EdsImageQuality_SR = 40173327;
        /**
         * SRAW(SRAW2) + Jpeg Large Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:691</i>
         */
        public static final int EdsImageQuality_SRLJF = 40108051;
        /**
         * SRAW(SRAW2) + Jpeg Large Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:692</i>
         */
        public static final int EdsImageQuality_SRLJN = 40108050;
        /**
         * SRAW(SRAW2) + Jpeg Middle Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:693</i>
         */
        public static final int EdsImageQuality_SRMJF = 40108307;
        /**
         * SRAW(SRAW2) + Jpeg Middle Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:694</i>
         */
        public static final int EdsImageQuality_SRMJN = 40108306;
        /**
         * SRAW(SRAW2) + Jpeg Small Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:695</i>
         */
        public static final int EdsImageQuality_SRSJF = 40108563;
        /**
         * SRAW(SRAW2) + Jpeg Small Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:696</i>
         */
        public static final int EdsImageQuality_SRSJN = 40108562;
        /**
         * SRAW(SRAW2) + Jpeg Small1 Fine<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:697</i>
         */
        public static final int EdsImageQuality_SRS1JF = 40111635;
        /**
         * SRAW(SRAW2) + Jpeg Small1 Normal<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:698</i>
         */
        public static final int EdsImageQuality_SRS1JN = 40111634;
        /**
         * SRAW(SRAW2) + Jpeg Small2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:699</i>
         */
        public static final int EdsImageQuality_SRS2JF = 40111891;
        /**
         * SRAW(SRAW2) + Jpeg Small3<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:700</i>
         */
        public static final int EdsImageQuality_SRS3JF = 40112147;
        /**
         * SRAW(SRAW2) + Jpeg Large<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:702</i>
         */
        public static final int EdsImageQuality_SRLJ = 40108048;
        /**
         * SRAW(SRAW2) + Jpeg Middle1<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:703</i>
         */
        public static final int EdsImageQuality_SRM1J = 40109328;
        /**
         * SRAW(SRAW2) + Jpeg Middle2<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:704</i>
         */
        public static final int EdsImageQuality_SRM2J = 40109584;
        /**
         * SRAW(SRAW2) + Jpeg Small<br>
         * <i>native declaration : EDSDK\Header\EDSDKTypes.h:705</i>
         */
        public static final int EdsImageQuality_SRSJ = 40108560;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:707</i>
        public static final int EdsImageQuality_Unknown = -1;
    };

    public static interface EdsImageType {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:593</i>
        public static final int kEdsImageType_Unknown = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:594</i>
        public static final int kEdsImageType_Jpeg = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:595</i>
        public static final int kEdsImageType_CRW = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:596</i>
        public static final int kEdsImageType_RAW = 4;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:597</i>
        public static final int kEdsImageType_CR2 = 6;
    };



    public static interface EdsSaveTo {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:817</i>
        public static final int kEdsSaveTo_Camera = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:818</i>
        public static final int kEdsSaveTo_Host = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:819</i>
        public static final int kEdsSaveTo_Both = EdSdkLibrary.EdsSaveTo.kEdsSaveTo_Camera |
                                                  EdSdkLibrary.EdsSaveTo.kEdsSaveTo_Host;
    };

    
    public static interface EdsPictureStyle {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:931</i>
        public static final int kEdsPictureStyle_Standard = 129;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:932</i>
        public static final int kEdsPictureStyle_Portrait = 130;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:933</i>
        public static final int kEdsPictureStyle_Landscape = 131;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:934</i>
        public static final int kEdsPictureStyle_Neutral = 132;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:935</i>
        public static final int kEdsPictureStyle_Faithful = 133;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:936</i>
        public static final int kEdsPictureStyle_Monochrome = 134;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:937</i>
        public static final int kEdsPictureStyle_Auto = 135;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:938</i>
        public static final int kEdsPictureStyle_User1 = 33;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:939</i>
        public static final int kEdsPictureStyle_User2 = 34;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:940</i>
        public static final int kEdsPictureStyle_User3 = 35;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:941</i>
        public static final int kEdsPictureStyle_PC1 = 65;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:942</i>
        public static final int kEdsPictureStyle_PC2 = 66;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:943</i>
        public static final int kEdsPictureStyle_PC3 = 67;
    };


    public static interface EdsTransferOption {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:952</i>
        public static final int kEdsTransferOption_ByDirectTransfer = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:953</i>
        public static final int kEdsTransferOption_ByRelease = 2;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:954</i>
        public static final int kEdsTransferOption_ToDesktop = 256;
    };

    
    public static interface EdsEvfAFMode {

        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:1023</i>
        public static final int Evf_AFMode_Quick = 0;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:1024</i>
        public static final int Evf_AFMode_Live = 1;
        /// <i>native declaration : EDSDK\Header\EDSDKTypes.h:1025</i>
        public static final int Evf_AFMode_LiveFace = 2;
    };

    
    NativeLong EdsCreateMemoryStream( NativeLong inBufferSize,
            EdSdkLibrary.EdsStreamRef.ByReference outStream );

    NativeLong EdsCreateEvfImageRef( EdSdkLibrary.EdsStreamRef inStreamRef,
            EdSdkLibrary.EdsEvfImageRef.ByReference outEvfImageRef );

    NativeLong EdsDownloadEvfImage( EdSdkLibrary.EdsCameraRef inCameraRef,
            EdSdkLibrary.EdsEvfImageRef inEvfImageRef );


    
    NativeLong EdsSetCapacity( EdSdkLibrary.EdsCameraRef inCameraRef,
            edsdk.bindings.EdsCapacity.ByValue inCapacity );

    
    NativeLong EdsGetDirectoryItemInfo( EdSdkLibrary.EdsDirectoryItemRef inDirItemRef,
            EdsDirectoryItemInfo outDirItemInfo );

    public interface EdsObjectEventHandler extends StdCallLibrary.StdCallCallback {

        NativeLong apply( NativeLong inEvent, EdSdkLibrary.EdsBaseRef inRef,
                          Pointer inContext );
    };

    NativeLong EdsInitializeSDK();

    NativeLong EdsTerminateSDK();

    NativeLong EdsRetain( EdSdkLibrary.EdsBaseRef inRef );

    NativeLong EdsRelease( EdSdkLibrary.EdsBaseRef inRef );

    NativeLong EdsGetChildCount( EdSdkLibrary.EdsBaseRef inRef,
                                 NativeLongByReference outCount );

    NativeLong EdsGetChildAtIndex( EdSdkLibrary.EdsBaseRef inRef,
                                   NativeLong inIndex,
                                   EdSdkLibrary.EdsBaseRef.ByReference outRef );
    NativeLong EdsGetParent( EdSdkLibrary.EdsBaseRef inRef,
                             EdSdkLibrary.EdsBaseRef.ByReference outParentRef );

    @Deprecated
    NativeLong EdsGetPropertySize( EdSdkLibrary.EdsBaseRef inRef,
                                   NativeLong inPropertyID, NativeLong inParam,
                                   IntByReference outDataType,
                                   NativeLongByReference outSize );

    NativeLong EdsGetPropertySize( EdSdkLibrary.EdsBaseRef inRef,
                                   NativeLong inPropertyID, NativeLong inParam,
                                   IntBuffer outDataType,
                                   NativeLongByReference outSize );

    NativeLong EdsGetPropertyData( EdSdkLibrary.EdsBaseRef inRef,
                                   NativeLong inPropertyID, NativeLong inParam,
                                   NativeLong inPropertySize,
                                   Pointer outPropertyData );

    NativeLong EdsSetPropertyData( EdSdkLibrary.EdsBaseRef inRef,
                                   NativeLong inPropertyID, NativeLong inParam,
                                   NativeLong inPropertySize,
                                   Pointer inPropertyData );

    NativeLong EdsGetPropertyDesc( EdSdkLibrary.EdsBaseRef inRef,
                                   NativeLong inPropertyID,
                                   EdsPropertyDesc outPropertyDesc );

    NativeLong EdsGetCameraList( EdSdkLibrary.EdsCameraListRef.ByReference outCameraListRef );

    NativeLong EdsSetObjectEventHandler( EdSdkLibrary.EdsCameraRef inCameraRef,
            NativeLong inEvnet,
            EdSdkLibrary.EdsObjectEventHandler inObjectEventHandler,
            Pointer inContext );

    
    NativeLong EdsOpenSession( EdSdkLibrary.EdsCameraRef inCameraRef );

    NativeLong EdsCloseSession( EdSdkLibrary.EdsCameraRef inCameraRef );

    NativeLong EdsSendCommand( EdSdkLibrary.EdsCameraRef inCameraRef,
                               NativeLong inCommand, NativeLong inParam );


    NativeLong EdsGetEvent();

    public abstract class EdsObjectByReference<T extends EdsBaseRef> extends ByReference {

        public EdsObjectByReference() {
            this( null );
        }

        public EdsObjectByReference( final T r ) {
            super( Pointer.SIZE );
            setValue( r );
        }

        public void setValue( final T r ) {
            getPointer().setPointer( 0, r != null ? r.getPointer() : null );
        }

        public abstract T getValue();
    }
    
    public static class EdsCameraListRef extends EdsBaseRef {

        public EdsCameraListRef() {
            super();
        }

        public EdsCameraListRef( final Pointer address ) {
            super( address );
        }

        public static class ByReference extends EdsBaseRef.ByReference {

            @Override
            public EdsCameraListRef getValue() {
                final Pointer p = getPointer().getPointer( 0 );
                if ( p == null ) {
                    return null;
                }
                return new EdsCameraListRef( p );
            }
        }
    }

    
    public static class EdsBaseRef extends PointerType {

        public EdsBaseRef() {
            super();
        }

        public EdsBaseRef( final Pointer address ) {
            super( address );
        }

        public static class ByReference extends EdsObjectByReference<EdsBaseRef> {

            @Override
            public EdsBaseRef getValue() {
                final Pointer p = getPointer().getPointer( 0 );
                if ( p == null ) {
                    return null;
                }
                return new EdsBaseRef( p );
            }

        }
    }
    
    public static class EdsStreamRef extends EdsBaseRef {

        public EdsStreamRef() {
            super();
        }

        public EdsStreamRef( final Pointer address ) {
            super( address );
        }

        public static class ByReference extends EdsBaseRef.ByReference {

            @Override
            public EdsStreamRef getValue() {
                final Pointer p = getPointer().getPointer( 0 );
                if ( p == null ) {
                    return null;
                }
                return new EdsStreamRef( p );
            }
        }
    }

    
    public static class EdsCameraRef extends EdsBaseRef {

        public EdsCameraRef() {
            super();
        }

        public EdsCameraRef( final Pointer address ) {
            super( address );
        }

        public static class ByReference extends EdsBaseRef.ByReference {

            @Override
            public EdsCameraRef getValue() {
                final Pointer p = getPointer().getPointer( 0 );
                if ( p == null ) {
                    return null;
                }
                return new EdsCameraRef( p );
            }
        }
    }
}
