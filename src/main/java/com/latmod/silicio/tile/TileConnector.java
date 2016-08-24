package com.latmod.silicio.tile;

import com.latmod.silicio.api.ISilNetController;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class TileConnector extends TileSilNet
{
    @Override
    public void provideSignals(@Nonnull ISilNetController c)
    {
    }

    @Override
    public void onSignalChanged(@Nonnull ISilNetController c, int channel, boolean on)
    {
    }
}