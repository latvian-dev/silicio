package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.module.IModuleProperty;
import com.latmod.silicio.api.module.IModulePropertyKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class ModulePropertyKey implements IModulePropertyKey
{
    private final String ID;
    private final IModuleProperty defValue;
    private final ITextComponent displayName;

    public ModulePropertyKey(String id, IModuleProperty def, @Nullable ITextComponent dn)
    {
        ID = id;
        defValue = def;
        displayName = dn;
    }

    @Override
    public String getName()
    {
        return ID;
    }

    @Override
    public IModuleProperty getDefValue()
    {
        return defValue;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return displayName == null ? new TextComponentString(getName()) : displayName;
    }

    public int hashCode()
    {
        return ID.hashCode();
    }

    public String toString()
    {
        return ID;
    }

    public boolean equals(Object o)
    {
        return o == this || (o != null && o.toString().equals(ID));
    }
}