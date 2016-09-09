package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.impl.EnumSignalSlot;
import com.latmod.silicio.api.module.impl.ModulePropertyKey;
import com.latmod.silicio.api.module.impl.PropertyShort;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleSequencer extends ModuleBase
{
    private static final ModulePropertyKey TIMER = new ModulePropertyKey("timer", new PropertyShort(20), null);

    private final List<EnumSignalSlot> outputs;

    public ModuleSequencer(int out)
    {
        outputs = new ArrayList<>(out);

        for(int i = 0; i < out; i++)
        {
            outputs.add(EnumSignalSlot.OUTPUT.get(i));
            properties.add(EnumSignalSlot.OUTPUT.get(i));
        }

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

        int timer = container.getProperty(TIMER).getInt();

        if(container.getTick() % (timer / 4) == 0L)
        {
            int i = (int) ((container.getTick() / timer) % 4);
            controller.provideSignal(container.getProperty(outputs.get(i)).getInt());
        }
    }
}