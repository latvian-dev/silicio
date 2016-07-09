package com.latmod.silicio.modules;

import com.latmod.silicio.api.SignalChannel;
import com.latmod.silicio.api.modules.EnumModuleIO;
import com.latmod.silicio.api.modules.Module;
import com.latmod.silicio.api.modules.ModuleContainer;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleTimer extends Module
{
    public ModuleTimer()
    {
        setFlags(getFlags() | FLAG_PROVIDE_SIGNALS);
    }

    @Override
    public void init(ModuleContainer c)
    {
        c.addConnection(EnumModuleIO.OUT_1);
    }

    @Override
    public void provideSignals(ModuleContainer c, Collection<SignalChannel> list)
    {
        if(c.tick % 20L == 0L)
        {
            list.add(c.getChannel(EnumModuleIO.OUT_1));
        }
    }
}