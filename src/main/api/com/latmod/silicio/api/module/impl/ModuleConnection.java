package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.EnumSignalSlot;
import com.latmod.silicio.api.module.ISignalConnectionProperty;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 27.08.2016.
 */
public class ModuleConnection extends ModulePropertyKey implements ISignalConnectionProperty
{
    private static final PropertyConnection EMPTY = new PropertyConnection()
    {
        @Override
        public void set(int v)
        {
        }

        @Override
        public boolean getBoolean()
        {
            return false;
        }

        @Override
        public int getInt()
        {
            return 0;
        }
    };

    private final EnumSignalSlot slot;

    public ModuleConnection(EnumSignalSlot s, @Nullable ITextComponent dn)
    {
        super(s.name(), EMPTY, dn);
        slot = s;
    }

    @Override
    public EnumSignalSlot getSlot()
    {
        return slot;
    }
}
