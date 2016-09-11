package com.latmod.silicio.api.module;

import com.feed_the_beast.ftbl.api.config.IConfigKey;

/**
 * Created by LatvianModder on 27.08.2016.
 */
public interface ISignalSlotPropertyKey extends IConfigKey
{
    ISignalSlot getSlot();
}