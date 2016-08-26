package com.latmod.silicio.api_impl.properties;

import com.latmod.silicio.api.IModuleProperty;
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

    public String getString()
    {
        return value;
    }

    public void set(String v)
    {
        value = v;
    }

    public String toString()
    {
        return String.valueOf(getString());
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