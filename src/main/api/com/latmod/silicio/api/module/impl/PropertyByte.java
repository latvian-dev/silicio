package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyByte implements IModuleProperty
{
    private byte value;

    public PropertyByte(int v)
    {
        value = (byte) v;
    }

    public void set(byte v)
    {
        value = v;
    }

    public byte getByte()
    {
        return value;
    }

    @Override
    public String getString()
    {
        return Byte.toString(getByte());
    }

    @Override
    public boolean getBoolean()
    {
        return getByte() != 0;
    }

    @Override
    public int getInt()
    {
        return getByte();
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyByte(getByte());
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagByte(getByte());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTPrimitive) nbt).getByte());
    }
}