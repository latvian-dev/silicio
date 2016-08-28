package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagShort;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyShort implements IModuleProperty
{
    private short value;

    public PropertyShort(int v)
    {
        value = (short) v;
    }

    public void set(short v)
    {
        value = v;
    }

    public short getShort()
    {
        return value;
    }

    @Override
    public String getString()
    {
        return String.valueOf(getShort());
    }

    @Override
    public boolean getBoolean()
    {
        return getShort() != 0;
    }

    @Override
    public int getInt()
    {
        return getShort();
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyShort(getShort());
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagShort(getShort());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTPrimitive) nbt).getShort());
    }
}