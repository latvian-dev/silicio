package com.latmod.silicio.api_impl.properties;

import com.latmod.silicio.api.IModuleProperty;
import com.latmod.silicio.api.IModulePropertyKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class ModulePropertyKey<N extends IModuleProperty> implements IModulePropertyKey<N>
{
    private final String ID;
    private final N defValue;
    private final ITextComponent displayName;

    public ModulePropertyKey(String id, N def, @Nullable ITextComponent dn)
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
    public N getDefValue()
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