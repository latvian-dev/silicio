package com.latmod.silicio.api_impl.properties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyByte extends PropertyNumber
{
    private byte value;

    public void set(byte v)
    {
        value = v;
    }

    public String toString()
    {
        return String.valueOf(getByte());
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

    @Override
    public byte getByte()
    {
        return value;
    }

    @Override
    public short getShort()
    {
        return getByte();
    }

    @Override
    public int getInt()
    {
        return getByte();
    }

    @Override
    public long getLong()
    {
        return getByte();
    }

    @Override
    public float getFloat()
    {
        return getByte();
    }

    @Override
    public double getDouble()
    {
        return getByte();
    }
}