package com.latmod.silicio.api_impl.properties;

import com.latmod.silicio.api.IModuleProperty;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public abstract class PropertyNumber implements IModuleProperty
{
    public abstract byte getByte();

    public abstract short getShort();

    public abstract int getInt();

    public abstract long getLong();

    public abstract float getFloat();

    public abstract double getDouble();
}