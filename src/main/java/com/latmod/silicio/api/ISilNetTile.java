package com.latmod.silicio.api;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ISilNetTile
{
    void provideSignals(ISilNetController c, ISignalBus channels);

    void onSignalChanged(ISilNetController c, int channel, boolean on);
}