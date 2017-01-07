package com.latmod.silicio.net;

import com.feed_the_beast.ftbl.lib.net.LMNetworkWrapper;
import com.latmod.silicio.Silicio;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public class SilicioNet
{
    static final LMNetworkWrapper NET = LMNetworkWrapper.newWrapper(Silicio.MOD_ID);

    public static void init()
    {
        NET.register(1, new MessagePipeItem());
    }
}