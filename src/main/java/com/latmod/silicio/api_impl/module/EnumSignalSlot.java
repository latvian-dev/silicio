package com.latmod.silicio.api_impl.module;

import com.feed_the_beast.ftbl.api.config.IConfigValue;
import com.feed_the_beast.ftbl.lib.LangKey;
import com.feed_the_beast.ftbl.lib.config.PropertyShort;
import com.feed_the_beast.ftbl.lib.config.SimpleConfigKey;
import com.latmod.silicio.api.module.ISignalSlot;
import com.latmod.silicio.api.module.ISignalSlotPropertyKey;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class EnumSignalSlot extends SimpleConfigKey implements ISignalSlotPropertyKey, ISignalSlot
{
    public static final EnumSignalSlot IN_1 = new EnumSignalSlot(0, false);
    public static final EnumSignalSlot IN_2 = new EnumSignalSlot(1, false);
    public static final EnumSignalSlot IN_3 = new EnumSignalSlot(2, false);
    public static final EnumSignalSlot IN_4 = new EnumSignalSlot(3, false);
    public static final EnumSignalSlot IN_5 = new EnumSignalSlot(4, false);
    public static final EnumSignalSlot IN_6 = new EnumSignalSlot(5, false);
    public static final EnumSignalSlot IN_7 = new EnumSignalSlot(6, false);
    public static final EnumSignalSlot IN_8 = new EnumSignalSlot(7, false);
    public static final EnumSignalSlot OUT_1 = new EnumSignalSlot(0, true);
    public static final EnumSignalSlot OUT_2 = new EnumSignalSlot(1, true);
    public static final EnumSignalSlot OUT_3 = new EnumSignalSlot(2, true);
    public static final EnumSignalSlot OUT_4 = new EnumSignalSlot(3, true);
    public static final EnumSignalSlot OUT_5 = new EnumSignalSlot(4, true);
    public static final EnumSignalSlot OUT_6 = new EnumSignalSlot(5, true);
    public static final EnumSignalSlot OUT_7 = new EnumSignalSlot(6, true);
    public static final EnumSignalSlot OUT_8 = new EnumSignalSlot(7, true);

    public static final List<EnumSignalSlot> VALUES = Collections.unmodifiableList(Arrays.asList(IN_1, IN_2, IN_3, IN_4, IN_5, IN_6, IN_7, IN_8, OUT_1, OUT_2, OUT_3, OUT_4, OUT_5, OUT_6, OUT_7, OUT_8));
    public static final List<EnumSignalSlot> INPUT = Collections.unmodifiableList(Arrays.asList(IN_1, IN_2, IN_3, IN_4, IN_5, IN_6, IN_7, IN_8));
    public static final List<EnumSignalSlot> OUTPUT = Collections.unmodifiableList(Arrays.asList(OUT_1, OUT_2, OUT_3, OUT_4, OUT_5, OUT_6, OUT_7, OUT_8));

    private final byte ID;
    private final IConfigValue defValue;
    private final ITextComponent displayName;

    private EnumSignalSlot(int i, boolean out)
    {
        super((out ? "out_" : "in_") + (i + 1));
        ID = (byte) ((out ? 8 : 0) + i);
        defValue = new PropertyShort(0).setMin((short) 0);
        displayName = (isInput() ? new LangKey("silicio.lang.connection.input") : new LangKey("silicio.lang.connection.output")).textComponent(getWrappedID() + 1);
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
    public ITextComponent getDisplayName()
    {
        return displayName;
    }

    @Override
    public boolean isInput()
    {
        return ID < 8;
    }

    @Override
    public int getWrappedID()
    {
        return ID % 8;
    }
}