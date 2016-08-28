package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class PropertyString implements IModuleProperty
{
    private String value;

    public PropertyString(String v)
    {
        value = v;
    }

    public void set(String v)
    {
        value = v;
    }

    @Override
    public String getString()
    {
        return String.valueOf(value);
    }

    @Override
    public boolean getBoolean()
    {
        return getString().equals("true");
    }

    @Override
    public int getInt()
    {
        return Integer.parseInt(getString());
    }

    @Override
    public IModuleProperty copy()
    {
        return new PropertyString(getString());
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagString(getString());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        set(((NBTTagString) nbt).getString());
    }
}