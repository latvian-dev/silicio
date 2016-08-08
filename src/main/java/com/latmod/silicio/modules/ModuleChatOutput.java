package com.latmod.silicio.modules;

import com.latmod.silicio.api.EnumSignalSlot;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleContainer;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput implements IModule
{
    @Override
    public void init(IModuleContainer container)
    {
        container.addConnection(EnumSignalSlot.IN_1);
    }

    @Override
    public void addRecipes(ItemStack stack)
    {
    }

    @Override
    public void onSignalChanged(IModuleContainer container, int channel, boolean on)
    {
        if(on && channel == container.getChannel(EnumSignalSlot.IN_1))
        {
        }
    }
}