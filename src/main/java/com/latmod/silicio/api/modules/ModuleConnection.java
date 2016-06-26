package com.latmod.silicio.api.modules;

import com.latmod.silicio.api.SignalChannel;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class ModuleConnection
{
    public final EnumModuleIO ID;
    private boolean enabled;
    private SignalChannel channel;

    public ModuleConnection(EnumModuleIO id)
    {
        ID = id;
        setEnabled(true);
        setChannel(SignalChannel.NULL);
    }

    //FIXME
    public static ModuleConnection getFromData(int data)
    {
        return new ModuleConnection(EnumModuleIO.IN_1);
    }

    //FIXME
    public int getData()
    {
        int data = channel == null ? 0 : channel.ID;
        return data;
    }

    public SignalChannel getChannel()
    {
        return channel == null ? SignalChannel.NULL : channel;
    }

    public void setChannel(SignalChannel c)
    {
        channel = (c == null || c.isInvalid()) ? null : c;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean b)
    {
        enabled = b;
    }

    @Override
    public int hashCode()
    {
        return ID.ordinal();
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this || o == ID || o.hashCode() == hashCode();
    }
}