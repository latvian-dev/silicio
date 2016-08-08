package com.latmod.silicio.tile;

import com.latmod.silicio.api.ISignalBus;
import com.latmod.silicio.api.ISilNetController;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class TileConnector extends TileSilNet
{
    @Override
    public void provideSignals(ISilNetController c, ISignalBus channels)
    {
    }

    @Override
    public void onSignalChanged(ISilNetController c, int channel, boolean on)
    {
    }
}