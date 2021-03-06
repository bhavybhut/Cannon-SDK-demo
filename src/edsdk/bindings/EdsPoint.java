package edsdk.bindings;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * <i>native declaration : EDSDK\Header\EDSDKTypes.h</i><br>
 * This file was autogenerated by <a
 * href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a
 * href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few
 * opensource projects.</a>.<br>
 * For help, please visit <a
 * href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a
 * href="http://rococoa.dev.java.net/">Rococoa</a>, or <a
 * href="http://jna.dev.java.net/">JNA</a>.
 */
public class EdsPoint extends Structure {

    /// C type : EdsInt32
    public NativeLong x;
    /// C type : EdsInt32
    public NativeLong y;

    public EdsPoint() {
        super();
        initFieldOrder();
    }

    protected void initFieldOrder() {
        setFieldOrder( new String[] { "x", "y" } );
    }

    /**
     * @param x C type : EdsInt32<br>
     * @param y C type : EdsInt32
     */
    public EdsPoint( final NativeLong x, final NativeLong y ) {
        super();
        this.x = x;
        this.y = y;
        initFieldOrder();
    }

    public EdsPoint( final Pointer pointer ) {
        super( pointer );
        read();
    }

    public static class ByReference extends EdsPoint implements Structure.ByReference {

    };

    public static class ByValue extends EdsPoint implements Structure.ByValue {

    };
}
