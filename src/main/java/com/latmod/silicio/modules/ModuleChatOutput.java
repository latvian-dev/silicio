package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.api_impl.config.ConfigKey;
import com.feed_the_beast.ftbl.api_impl.config.PropertyString;
import com.latmod.lib.util.LMServerUtils;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.impl.EnumSignalSlot;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import gnu.trove.map.TIntByteMap;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput extends ModuleBase
{
    private static final ConfigKey CHAT_MESSAGE = new ConfigKey("message", new PropertyString("Text"), "Chat Message", false); //TODO: Lang

    public ModuleChatOutput()
    {
        properties.add(EnumSignalSlot.IN_1);
        properties.add(CHAT_MESSAGE);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void onSignalsChanged(ISocketBlock socketBlock, ISilNetController controller, TIntByteMap channels)
    {
        IModuleContainer container = socketBlock.getContainer();

        if(channels.get(container.getProperty(EnumSignalSlot.IN_1).getInt()) == 1)
        {
            LMServerUtils.printChat(null, container.getProperty(CHAT_MESSAGE).getString());
        }
    }
}