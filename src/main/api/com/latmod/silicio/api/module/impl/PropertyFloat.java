package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyFloat implements IModuleProperty
{
    private float value;

    public PropertyFloat(float v)
    {
        value = v;
    }

    public void set(float v)
    {
        value = v;
    }

    public float getFloat()
    {
        return value;
    }

    @Override
    public String getString()
    {
        return Float.toString(getFloat());
    }

    @Override
    public boolean getBoolean()
    {
        return getFloat() != 0F;
    }

    @Override
    public int getInt()
    {
        return (int) getFloat();
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyFloat(getFloat());
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagFloat(getFloat());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTPrimitive) nbt).getFloat());
    }
}