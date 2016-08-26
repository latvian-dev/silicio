package com.latmod.silicio.api_impl.properties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyInt extends PropertyNumber
{
    private int value;

    public void set(int v)
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
        return new NBTTagInt(getInt());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTPrimitive) nbt).getInt());
    }

    @Override
    public byte getByte()
    {
        return (byte) getInt();
    }

    @Override
    public short getShort()
    {
        return (short) getInt();
    }

    @Override
    public int getInt()
    {
        return value;
    }

    @Override
    public long getLong()
    {
        return getInt();
    }

    @Override
    public float getFloat()
    {
        return getInt();
    }

    @Override
    public double getDouble()
    {
        return getInt();
    }
}