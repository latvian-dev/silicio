package com.latmod.silicio.api_impl;

import com.latmod.lib.EmptyCapStorage;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISilNetTile;
import com.latmod.silicio.api.tile.ISocketBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by LatvianModder on 16.09.2016.
 */
public class SilCaps
{
    @CapabilityInject(IModuleContainer.class)
    public static Capability<IModuleContainer> MODULE_CONTAINER = null;

    @CapabilityInject(ISilNetTile.class)
    public static Capability<ISilNetTile> SILNET_TILE = null;

    @CapabilityInject(ISocketBlock.class)
    public static Capability<ISocketBlock> SOCKET_BLOCK = null;

    public static void init()
    {
        CapabilityManager.INSTANCE.register(IModuleContainer.class, new EmptyCapStorage<>(), () -> null);
        CapabilityManager.INSTANCE.register(ISilNetTile.class, new EmptyCapStorage<>(), () -> null);
        CapabilityManager.INSTANCE.register(ISocketBlock.class, new EmptyCapStorage<>(), () -> null);
    }
}