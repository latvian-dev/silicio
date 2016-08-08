package com.latmod.silicio.api;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface ISignalBus
{
    boolean getSignal(int id);

    void setSignal(int id, boolean on);
}