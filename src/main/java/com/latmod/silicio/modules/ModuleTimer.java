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
public class ModuleTimer implements IModule
{
    private static final ModuleConnection OUT_1 = new ModuleConnection(EnumSignalSlot.OUT_1, null);
    private static final ModulePropertyKey TIMER = new ModulePropertyKey("timer", new PropertyShort(20), null);

    @Override
    public void init(IModuleContainer container)
    {
        container.addProperty(OUT_1);
        container.addProperty(TIMER);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void provideSignals(IModuleContainer container, ISilNetController controller)
    {
        if(container.getTick() % container.getProperty(TIMER).getInt() == 0L)
        {
            controller.provideSignal(container.getProperty(OUT_1).getInt());
        }
    }
}