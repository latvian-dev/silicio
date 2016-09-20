package com.latmod.silicio;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.FTBLibAddon;
import com.feed_the_beast.ftbl.api.IFTBLibAddon;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.gui.SilGuis;
import com.latmod.silicio.item.SilItems;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LatvianModder on 20.09.2016.
 */
@FTBLibAddon
public class FTBLibIntegration implements IFTBLibAddon
{
    public static FTBLibAPI API;

    @Override
    public void onFTBLibLoaded(FTBLibAPI api)
    {
        API = api;
        API.getRegistries().recipeHandlers().register(new ResourceLocation(Silicio.MOD_ID, "blocks"), new SilBlocks.Recipes());
        API.getRegistries().recipeHandlers().register(new ResourceLocation(Silicio.MOD_ID, "items"), new SilItems.Recipes());
        API.getRegistries().recipeHandlers().register(new ResourceLocation(Silicio.MOD_ID, "modules"), new SilItems.Modules.Recipes());
        SilGuis.init(API.getRegistries());
    }
}