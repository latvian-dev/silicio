package com.latmod.silicio.api_impl.properties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagShort;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyShort extends PropertyNumber
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

    public String toString()
    {
        return String.valueOf(getShort());
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

    @Override
    public byte getByte()
    {
        return (byte) getShort();
    }

    @Override
    public short getShort()
    {
        return value;
    }

    @Override
    public int getInt()
    {
        return getShort();
    }

    @Override
    public long getLong()
    {
        return getShort();
    }

    @Override
    public float getFloat()
    {
        return getShort();
    }

    @Override
    public double getDouble()
    {
        return getShort();
    }
}