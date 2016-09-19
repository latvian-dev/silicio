package com.latmod.silicio.api.module.impl;

import com.feed_the_beast.ftbl.api.config.IConfigValue;
import com.feed_the_beast.ftbl.api_impl.config.PropertyInt;
import com.latmod.lib.EnumNameMap;
import com.latmod.lib.LangKey;
import com.latmod.silicio.api.module.ISignalSlot;
import com.latmod.silicio.api.module.ISignalSlotPropertyKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
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

    private final String name;
    private final IConfigValue defValue;
    private final ITextComponent displayName;

    EnumSignalSlot()
    {
        name = EnumNameMap.createName(this);
        defValue = new PropertyInt(Constants.NBT.TAG_SHORT, 0).setMin(0).setMax(Short.MAX_VALUE);
        displayName = (isInput() ? new LangKey("silicio.lang.connection.input") : new LangKey("silicio.lang.connection.output")).textComponent(getWrappedID() + 1);
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
    public IConfigValue getDefValue()
    {
        return defValue;
    }

    @Override
    @Nullable
    public String getRawDisplayName()
    {
        return null;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return displayName;
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

    @Override
    public String getInfo()
    {
        return "";
    }

    @Override
    public void setInfo(String s)
    {
    }

    @Override
    public int getFlags()
    {
        return 0;
    }

    @Override
    public void setFlags(int flags)
    {
    }
}