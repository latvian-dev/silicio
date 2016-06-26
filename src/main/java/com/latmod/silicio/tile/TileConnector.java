package com.latmod.silicio.tile;

import com.latmod.silicio.api.SignalChannel;
import com.latmod.silicio.api.tile.ISilNetController;

import java.util.Collection;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class TileConnector extends TileSilNet
{
    @Override
    public void provideSignals(ISilNetController c, Collection<SignalChannel> channels)
    {

    }

    @Override
    public void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on)
    {

    }
}