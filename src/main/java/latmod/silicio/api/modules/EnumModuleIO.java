package latmod.silicio.api.modules;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public enum EnumModuleIO
{
    IN_1,
    IN_2,
    IN_3,
    IN_4,
    IN_5,
    IN_6,
    IN_7,
    IN_8,
    OUT_1,
    OUT_2,
    OUT_3,
    OUT_4,
    OUT_5,
    OUT_6,
    OUT_7,
    OUT_8;
    
    public final boolean input;
    public final int wrappedID;
    
    EnumModuleIO()
    {
        input = ordinal() < 8;
        wrappedID = ordinal() % 8;
    }
    
    public static final EnumModuleIO[] VALUES = values();
    public static final EnumModuleIO[] INPUT = {IN_1, IN_2, IN_3, IN_4, IN_5, IN_6, IN_7, IN_8};
    public static final EnumModuleIO[] OUTPUT = {OUT_1, OUT_2, OUT_3, OUT_4, OUT_5, OUT_6, OUT_7, OUT_8};
}