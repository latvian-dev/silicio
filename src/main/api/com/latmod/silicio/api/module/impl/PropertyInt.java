package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyInt implements IModuleProperty
{
    private int value;

    public PropertyInt(int v)
    {
        value = v;
    }

    public void set(int v)
    {
        value = v;
    }

    @Override
    public String getString()
    {
        return Integer.toString(getInt());
    }

    @Override
    public boolean getBoolean()
    {
        return getInt() != 0;
    }

    @Override
    public int getInt()
    {
        return value;
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyInt(getInt());
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
}