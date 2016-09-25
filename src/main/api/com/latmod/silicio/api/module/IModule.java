package com.latmod.silicio.api.module;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import gnu.trove.map.TShortByteMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModule
{
    ResourceLocation getID();

    default Collection<IConfigKey> getProperties()
    {
        return Collections.emptyList();
    }

    default void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    default ModelResourceLocation getModelLocation()
    {
        return new ModelResourceLocation(new ResourceLocation(getID().getResourceDomain(), "modules/" + getID().getResourcePath()), "inventory");
    }

    default String getUnlocalizedName()
    {
        ResourceLocation id = getID();
        return id.getResourceDomain() + ".item.module." + id.getResourcePath();
    }

    default void onAdded(ISocketBlock socketBlock, EntityPlayerMP player)
    {
    }

    default void onRemoved(ISocketBlock socketBlock, EntityPlayerMP player)
    {
    }

    default void onUpdate(ISocketBlock socketBlock)
    {
    }

    default void provideSignals(ISocketBlock socketBlock, ISilNetController controller)
    {
    }

    default void onSignalsChanged(ISocketBlock socketBlock, ISilNetController controller, TShortByteMap channels)
    {
    }
}
