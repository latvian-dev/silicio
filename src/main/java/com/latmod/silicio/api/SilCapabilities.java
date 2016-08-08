package com.latmod.silicio.api;

import com.feed_the_beast.ftbl.util.EmptyCapStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class SilCapabilities
{
    @CapabilityInject(IModuleProvider.class)
    public static Capability<IModuleProvider> MODULE_PROVIDER = null;

    @CapabilityInject(ISilNetTile.class)
    public static Capability<ISilNetTile> SILNET_TILE = null;

    private static boolean enabled = false;

    /**
     * Call this if you use the API
     */
    public static void enable()
    {
        if(!enabled)
        {
            enabled = true;

            CapabilityManager.INSTANCE.register(IModuleProvider.class, new EmptyCapStorage<>(), () -> null);
            CapabilityManager.INSTANCE.register(ISilNetTile.class, new EmptyCapStorage<>(), () -> null);
        }
    }
}
