package com.latmod.silicio.api.module.impl;

import com.latmod.lib.EnumNameMap;
import com.latmod.lib.LangKey;
import com.latmod.silicio.api.module.IModuleProperty;
import com.latmod.silicio.api.module.ISignalSlot;
import com.latmod.silicio.api.module.ISignalSlotPropertyKey;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public enum EnumSignalSlot implements ISignalSlotPropertyKey, ISignalSlot
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
    private static final LangKey LANG_KEY_INPUT = new LangKey("silicio.lang.connection.input");
    private static final LangKey LANG_KEY_OUTPUT = new LangKey("silicio.lang.connection.output");

    private final String name;
    private final PropertyConnection defValue;

    EnumSignalSlot()
    {
        name = EnumNameMap.createName(this);
        defValue = new PropertyConnection();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public ISignalSlot getSlot()
    {
        return this;
    }

    @Override
    public IModuleProperty getDefValue()
    {
        return defValue;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return (isInput() ? LANG_KEY_INPUT : LANG_KEY_OUTPUT).textComponent(getWrappedID() + 1);
    }

    @Override
    public boolean isInput()
    {
        return ordinal() < 8;
    }

    @Override
    public int getWrappedID()
    {
        return ordinal() % 8;
    }
}