package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.api.EnumSignalSlot;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleContainer;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api_impl.properties.ModulePropertyKey;
import com.latmod.silicio.api_impl.properties.PropertyString;
import gnu.trove.map.TIntByteMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput implements IModule
{
    private static final ModulePropertyKey<PropertyString> CHAT_MESSAGE = new ModulePropertyKey<>("message", new PropertyString("Text"), new TextComponentString("Chat Message")); //TODO: Lang

    @Override
    public void init(IModuleContainer container)
    {
        container.addConnection(EnumSignalSlot.IN_1);
        container.addProperty(CHAT_MESSAGE);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void onSignalsChanged(IModuleContainer container, ISilNetController controller, TIntByteMap channels)
    {
        if(channels.get(container.getChannel(EnumSignalSlot.IN_1)) == 1)
        {
            FTBLib.printChat(null, container.getProperty(CHAT_MESSAGE).getString());
        }
    }
}