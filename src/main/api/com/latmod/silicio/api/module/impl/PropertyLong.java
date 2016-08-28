package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagLong;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyLong implements IModuleProperty
{
    private long value;

    public PropertyLong(long v)
    {
        value = v;
    }

    public void set(long v)
    {
        value = v;
    }

    public long getLong()
    {
        return value;
    }

    @Override
    public String getString()
    {
        return Long.toString(getLong());
    }

    @Override
    public boolean getBoolean()
    {
        return getLong() != 0L;
    }

    @Override
    public int getInt()
    {
        return (int) getLong();
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyLong(getLong());
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagLong(getLong());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTPrimitive) nbt).getLong());
    }
}