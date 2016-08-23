package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.EnumSignalSlot;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleContainer;
import com.latmod.silicio.api.ISignalBus;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleTimer implements IModule
{
    @Override
    public void init(IModuleContainer container)
    {
        container.addConnection(EnumSignalSlot.OUT_1);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void provideSignals(IModuleContainer container, ISignalBus list)
    {
        if(container.getTick() % 20L == 0L)
        {
            list.setSignal(container.getChannel(EnumSignalSlot.OUT_1), true);
        }
    }
}