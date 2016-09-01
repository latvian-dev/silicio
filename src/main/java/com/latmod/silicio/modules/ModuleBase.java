package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModulePropertyKey;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import gnu.trove.map.TIntByteMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by LatvianModder on 1.09.2016.
 */
public class ModuleBase implements IModule
{
    protected final Collection<IModulePropertyKey> properties = new ArrayList<>();

    @Override
    public Collection<IModulePropertyKey> getProperties()
    {
        return properties.isEmpty() ? Collections.emptyList() : Collections.unmodifiableCollection(properties);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
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