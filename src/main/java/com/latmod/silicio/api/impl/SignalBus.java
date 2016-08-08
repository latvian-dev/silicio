package com.latmod.silicio.api.impl;

import com.latmod.silicio.api.ISignalBus;
import gnu.trove.TIntCollection;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public final class SignalBus implements ISignalBus
{
    private final TIntCollection signals = new TIntHashSet();

    public void clear()
    {
        signals.clear();
    }

    @Override
    public boolean getSignal(int id)
    {
        return signals.contains(id);
    }

    @Override
    public void setSignal(int id, boolean on)
    {
        if(on)
        {
            signals.add(id);
        }
        else
        {
            signals.remove(id);
        }
    }

    public void read(NBTTagCompound tag, String s)
    {
        signals.clear();
        signals.addAll(tag.getIntArray(s));
    }

    public void write(NBTTagCompound tag, String s)
    {
        tag.setIntArray(s, signals.toArray());
    }
}