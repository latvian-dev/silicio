package com.latmod.silicio.api;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModule
{
    void init(IModuleContainer container);

    default void addRecipes(ItemStack stack)
    {
    }

    default void onAdded(IModuleContainer container, EntityPlayerMP player)
    {
    }

    default void onRemoved(IModuleContainer container, EntityPlayerMP player)
    {
    }

    default void onUpdate(IModuleContainer container)
    {
    }

    default void provideSignals(IModuleContainer container, ISignalBus channels)
    {
    }

    default void onSignalChanged(IModuleContainer container, int channel, boolean on)
    {
    }
}
