package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.module.EnumSignalSlot;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.impl.ModuleConnection;
import com.latmod.silicio.api.module.impl.ModulePropertyKey;
import com.latmod.silicio.api.module.impl.PropertyShort;
import com.latmod.silicio.api.tile.ISilNetController;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleSequencer implements IModule
{
    private static final ModulePropertyKey TIMER = new ModulePropertyKey("timer", new PropertyShort(20), null);

    private final ModuleConnection[] outputs;

    public ModuleSequencer(int out)
    {
        outputs = new ModuleConnection[out];

        for(int i = 0; i < out; i++)
        {
            outputs[i] = new ModuleConnection(EnumSignalSlot.OUTPUT.get(i), null);
        }
    }

    @Override
    public void init(IModuleContainer container)
    {
        for(ModuleConnection c : outputs)
        {
            container.addProperty(c);
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
        int timer = container.getProperty(TIMER).getInt();

        if(container.getTick() % (timer / 4) == 0L)
        {
            int i = (int) ((container.getTick() / timer) % 4);
            controller.provideSignal(container.getProperty(outputs[i]).getInt());
        }
    }
}