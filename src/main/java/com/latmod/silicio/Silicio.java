package com.latmod.silicio;

import com.feed_the_beast.ftbl.util.CreativeTabLM;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.api.SilCapabilities;
import com.latmod.silicio.api.SilNet;
import com.latmod.silicio.block.BlockSilMachines;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.item.SilItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:ftbl;required-after:tesla;required-after:mcmultipart")
//;required-after:mcmultipart
public class Silicio
{
    public static final String MOD_ID = "silicio";

    @Mod.Instance(MOD_ID)
    public static Silicio inst;

    @SidedProxy(clientSide = "com.latmod.silicio.client.SilClient", serverSide = "com.latmod.silicio.SilCommon")
    public static SilCommon proxy;

    public static CreativeTabLM tab;

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

        tab.addIcon(BlockSilMachines.EnumVariant.CONTROLLER.getStack(1));

        SilCapabilities.enable();

        proxy.preInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event)
    {
        SilBlocks.loadRecipes();
        SilItems.loadRecipes();
    }

    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event)
    {
        SilNet.clear();
    }
}