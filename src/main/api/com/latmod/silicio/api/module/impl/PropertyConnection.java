package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;

/**
 * Created by LatvianModder on 27.08.2016.
 */
public class PropertyConnection implements IModuleProperty
{
    private int channel;

    public void set(int v)
    {
        channel = v;
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
        return channel;
    }

    @Override
    public IModuleProperty copy()
    {
        PropertyConnection p = new PropertyConnection();
        p.set(getInt());
        return p;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagInt(channel);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        channel = ((NBTPrimitive) nbt).getInt();
    }
}
