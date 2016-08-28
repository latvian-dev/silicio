package com.latmod.silicio.api.module;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public static final List<EnumSignalSlot> INPUT = Collections.unmodifiableList(Arrays.asList(IN_1, IN_2, IN_3, IN_4, IN_5, IN_6, IN_7, IN_8));
    public static final List<EnumSignalSlot> OUTPUT = Collections.unmodifiableList(Arrays.asList(OUT_1, OUT_2, OUT_3, OUT_4, OUT_5, OUT_6, OUT_7, OUT_8));

    public boolean isInput()
    {
        return ordinal() < 8;
    }

    public int getWrappedID()
    {
        return ordinal() % 8;
    }
}