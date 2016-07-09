package com.latmod.silicio.api.modules;

import com.latmod.lib.annotations.IFlagContainer;
import com.latmod.silicio.api.SignalChannel;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public abstract class Module implements IFlagContainer
{
    public static final byte FLAG_PROVIDE_SIGNALS = 1;

    private int flags = 0;

    public int getFlags()
    {
        return flags;
    }

    public void setFlags(int f)
    {
        flags = f;
    }

    public void init(ModuleContainer c)
    {
    }

    public void onAdded(ModuleContainer c, EntityPlayerMP player)
    {
    }

    public void onRemoved(ModuleContainer c, EntityPlayerMP player)
    {
    }

    public void onUpdate(ModuleContainer c)
    {
    }

    public void provideSignals(ModuleContainer c, Collection<SignalChannel> list)
    {
    }

    public void onSignalChanged(ModuleContainer c, SignalChannel id, boolean on)
    {
    }
}