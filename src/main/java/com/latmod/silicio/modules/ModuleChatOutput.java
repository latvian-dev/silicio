package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.api.module.EnumSignalSlot;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.impl.ModuleConnection;
import com.latmod.silicio.api.module.impl.ModulePropertyKey;
import com.latmod.silicio.api.module.impl.PropertyString;
import com.latmod.silicio.api.tile.ISilNetController;
import gnu.trove.map.TIntByteMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput implements IModule
{
    private static final ModuleConnection IN_1 = new ModuleConnection(EnumSignalSlot.IN_1, null);
    private static final ModulePropertyKey CHAT_MESSAGE = new ModulePropertyKey("message", new PropertyString("Text"), new TextComponentString("Chat Message")); //TODO: Lang

    @Override
    public void init(IModuleContainer container)
    {
        container.addProperty(IN_1);
        container.addProperty(CHAT_MESSAGE);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void onSignalsChanged(IModuleContainer container, ISilNetController controller, TIntByteMap channels)
    {
        if(channels.get(container.getProperty(IN_1).getInt()) == 1)
        {
            FTBLib.printChat(null, container.getProperty(CHAT_MESSAGE).getString());
        }
    }
}