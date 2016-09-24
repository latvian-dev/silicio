package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import gnu.trove.map.TIntByteMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by LatvianModder on 1.09.2016.
 */
public class ModuleBase implements IModule
{
    protected final Collection<IConfigKey> properties = new ArrayList<>();

    @Override
    public Collection<IConfigKey> getProperties()
    {
        return properties.isEmpty() ? Collections.emptyList() : Collections.unmodifiableCollection(properties);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void addModel(Item item, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(item.getRegistryName().getResourceDomain(), "modules/" + id), "inventory"));
    }

    @Override
    public void onAdded(ISocketBlock socketBlock, EntityPlayerMP player)
    {
    }

    @Override
    public void onRemoved(ISocketBlock socketBlock, EntityPlayerMP player)
    {
    }

    @Override
    public void onUpdate(ISocketBlock socketBlock)
    {
    }

    @Override
    public void provideSignals(ISocketBlock socketBlock, ISilNetController controller)
    {
    }

    @Override
    public void onSignalsChanged(ISocketBlock socketBlock, ISilNetController controller, TIntByteMap channels)
    {
    }
}