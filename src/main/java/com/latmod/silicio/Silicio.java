package com.latmod.silicio;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.util.CreativeTabLM;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.api.SilCapabilities;
import com.latmod.silicio.api.SilNet;
import com.latmod.silicio.block.EnumSilBlocks;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.gui.SilGuis;
import com.latmod.silicio.item.SilItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:ftbl;required-after:tesla;required-after:mcmultipart")
//;required-after:mcmultipart
public class Silicio
{
    public static final String MOD_ID = "silicio";

    @Mod.Instance(MOD_ID)
    public static Silicio INST;

    @SidedProxy(clientSide = "com.latmod.silicio.client.SilClient", serverSide = "com.latmod.silicio.SilCommon")
    public static SilCommon proxy;

    public CreativeTabLM tab;

    public static <K extends IForgeRegistryEntry<?>> K register(String id, K obj)
    {
        return FTBLib.register(new ResourceLocation(MOD_ID, id), obj);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        tab = new CreativeTabLM("silicio");
        //SilConfig.load();

        SilItems.init();
        SilBlocks.init();
        SilSounds.init();

        tab.addIcon(EnumSilBlocks.CONTROLLER.getStack(1));

        SilCapabilities.enable();
        SilGuis.init();

        proxy.preInit();

        FTBLibAPI.get().getRegistries().recipeHandlers().register(new ResourceLocation(MOD_ID, "blocks"), new SilBlocks.Recipes());
        FTBLibAPI.get().getRegistries().recipeHandlers().register(new ResourceLocation(MOD_ID, "items"), new SilItems.Recipes());
        FTBLibAPI.get().getRegistries().recipeHandlers().register(new ResourceLocation(MOD_ID, "modules"), new SilItems.Modules.Recipes());
    }

    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event)
    {
        SilNet.clear();
    }
}