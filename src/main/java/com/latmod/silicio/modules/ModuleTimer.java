package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.lib.config.ConfigKey;
import com.latmod.lib.config.PropertyShort;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import com.latmod.silicio.api_impl.module.EnumSignalSlot;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleTimer extends ModuleSil
{
    private static final ConfigKey TIMER = new ConfigKey("timer", new PropertyShort(20, 0, 32000));

    public ModuleTimer()
    {
        super("timer");
        properties.add(EnumSignalSlot.OUT_1);
        properties.add(TIMER);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void provideSignals(ISocketBlock socketBlock, ISilNetController controller)
    {
        IModuleContainer container = socketBlock.getContainer();

        if(container.getTick() % container.getProperty(TIMER).getInt() == 0L)
        {
            controller.provideSignal((short) container.getProperty(EnumSignalSlot.OUT_1).getInt());
        }
    }
}