package com.latmod.silicio.api.module;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import gnu.trove.map.TIntByteMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import java.util.Collection;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModule
{
    Collection<IModulePropertyKey> getProperties();

    void addRecipes(ItemStack stack, IRecipes recipes);

    void onAdded(ISocketBlock socketBlock, EntityPlayerMP player);

    void onRemoved(ISocketBlock socketBlock, EntityPlayerMP player);

    void onUpdate(ISocketBlock socketBlock);

    void provideSignals(ISocketBlock socketBlock, ISilNetController controller);

    void onSignalsChanged(ISocketBlock socketBlock, ISilNetController controller, TIntByteMap channels);
}
