package com.latmod.silicio.api;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import gnu.trove.map.TIntByteMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModule
{
    void init(IModuleContainer container);

    default void addRecipes(ItemStack stack, IRecipes recipes)
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

    default void provideSignals(IModuleContainer container, ISilNetController controller)
    {
    }

    default void onSignalsChanged(IModuleContainer container, ISilNetController controller, TIntByteMap channels)
    {
    }
}
