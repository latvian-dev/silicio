package com.latmod.silicio.api.module;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.config.IConfigValue;
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

    IConfigValue getProperty(IConfigKey config);

    Map<IConfigKey, IConfigValue> getProperties();
}