package com.latmod.silicio.api;

/**
 * Created by LatvianModder on 15.11.2016.
 */
public interface ISilicioPlugin
{
    void init(SilicioAPI api);

    default void registerCommon(ISilicioRegistry reg)
    {
    }
}