package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import gnu.trove.map.TIntByteMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by LatvianModder on 1.09.2016.
 */
public class ModuleBase implements IModule
{
    private final ResourceLocation ID;
    protected final Collection<IConfigKey> properties;
    private ModelResourceLocation model;

    public ModuleBase(ResourceLocation id)
    {
        ID = id;
        properties = new ArrayList<>();
    }

    @Override
    public ResourceLocation getID()
    {
        return ID;
    }

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
    public ModelResourceLocation getModelLocation()
    {
        if(model == null)
        {
            model = new ModelResourceLocation(new ResourceLocation(getID().getResourceDomain(), "modules/" + getID().getResourcePath()), "inventory");
        }

        return model;
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