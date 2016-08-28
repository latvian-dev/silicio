package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagDouble;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyDouble implements IModuleProperty
{
    private double value;

    public PropertyDouble(double v)
    {
        value = v;
    }

    public void set(double v)
    {
        value = v;
    }

    public double getDouble()
    {
        return value;
    }

    @Override
    public String getString()
    {
        return Double.toString(getDouble());
    }

    @Override
    public boolean getBoolean()
    {
        return getDouble() != 0D;
    }

    @Override
    public int getInt()
    {
        return (int) getDouble();
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyDouble(getDouble());
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagDouble(getDouble());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTPrimitive) nbt).getDouble());
    }
}