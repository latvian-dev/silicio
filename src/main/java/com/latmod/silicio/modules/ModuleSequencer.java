package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.EnumSignalSlot;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleContainer;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api_impl.properties.ModulePropertyKey;
import com.latmod.silicio.api_impl.properties.PropertyShort;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleSequencer implements IModule
{
    private static final ModulePropertyKey<PropertyShort> TIMER = new ModulePropertyKey<>("timer", new PropertyShort(20), null);

    private final int outputs;

    public ModuleSequencer(int out)
    {
        outputs = out;
    }

    @Override
    public void init(IModuleContainer container)
    {
        for(int i = 0; i < outputs; i++)
        {
            container.addConnection(EnumSignalSlot.OUTPUT[i]);
        }

        container.addProperty(TIMER);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void provideSignals(IModuleContainer container, ISilNetController controller)
    {
        long timer = container.getProperty(TIMER).getLong();

        if(container.getTick() % (timer / outputs) == 0L)
        {
            int i = (int) ((container.getTick() / timer) % outputs);
            controller.provideSignal(container.getChannel(EnumSignalSlot.OUTPUT[i]));
        }
    }
}