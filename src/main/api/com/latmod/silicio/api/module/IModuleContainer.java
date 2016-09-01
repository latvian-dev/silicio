package com.latmod.silicio.api.module;

import com.latmod.silicio.api.tile.ISocketBlock;

import java.util.Map;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModuleContainer
{
    IModule getModule();

    long getTick();

    void tick(ISocketBlock socketBlock);

    IModuleProperty getProperty(IModulePropertyKey config);

    Map<IModulePropertyKey, IModuleProperty> getProperties();
}