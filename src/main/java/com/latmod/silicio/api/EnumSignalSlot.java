package com.latmod.silicio.api;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public enum EnumSignalSlot
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

    public static final EnumSignalSlot[] VALUES = values();
    public static final EnumSignalSlot[] INPUT = {IN_1, IN_2, IN_3, IN_4, IN_5, IN_6, IN_7, IN_8};
    public static final EnumSignalSlot[] OUTPUT = {OUT_1, OUT_2, OUT_3, OUT_4, OUT_5, OUT_6, OUT_7, OUT_8};

    public boolean isInput()
    {
        return ordinal() < 8;
    }

    public int getWrappedID()
    {
        return ordinal() % 8;
    }
}