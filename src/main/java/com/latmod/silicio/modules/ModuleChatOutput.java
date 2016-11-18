package com.latmod.silicio.modules;

import com.feed_the_beast.ftbl.api.IRecipes;
import com.feed_the_beast.ftbl.lib.config.ConfigKey;
import com.feed_the_beast.ftbl.lib.config.PropertyString;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISocketBlock;
import com.latmod.silicio.api_impl.module.EnumSignalSlot;
import gnu.trove.map.TShortByteMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput extends ModuleSil
{
    private static final ConfigKey CHAT_MESSAGE = new ConfigKey("message", new PropertyString("Text"), new TextComponentTranslation("silicio.item.module.chat_out.chat_message"));

    public ModuleChatOutput()
    {
        super("chat_out");
        properties.add(EnumSignalSlot.IN_1);
        properties.add(CHAT_MESSAGE);
    }

    @Override
    public void addRecipes(ItemStack stack, IRecipes recipes)
    {
    }

    @Override
    public void onSignalsChanged(ISocketBlock socketBlock, ISilNetController controller, TShortByteMap channels)
    {
        IModuleContainer container = socketBlock.getContainer();

        if(channels.get((short) container.getProperty(EnumSignalSlot.IN_1).getInt()) == 1)
        {
            //LMServerUtils.printChat(null, container.getProperty(CHAT_MESSAGE).getString());
        }
    }
}