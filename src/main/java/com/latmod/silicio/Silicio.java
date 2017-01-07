package com.latmod.silicio;

import com.feed_the_beast.ftbl.lib.CreativeTabLM;
import com.feed_the_beast.ftbl.lib.util.LMUtils;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.api_impl.SilicioAPI_Impl;
import com.latmod.silicio.block.EnumSilBlocks;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.net.SilicioNet;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

@Mod(modid = Silicio.MOD_ID, name = "Silicio 2", version = "0.0.0", useMetadata = true, dependencies = "required-after:ftbl")
public class Silicio
{
    public static final String MOD_ID = "silicio";

    @Mod.Instance(MOD_ID)
    public static Silicio INST;

    @SidedProxy(clientSide = "com.latmod.silicio.client.SilClient", serverSide = "com.latmod.silicio.SilCommon")
    public static SilCommon PROXY;

    public CreativeTabLM tab;

    public static <K extends IForgeRegistryEntry<?>> void register(String id, K obj)
    {
        LMUtils.register(new ResourceLocation(MOD_ID, id), obj);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        tab = new CreativeTabLM("silicio");
        SilicioAPI_Impl.INSTANCE.init(event.getAsmData());

        SilItems.init();
        SilBlocks.init();
        SilSounds.init();
        SilCaps.init();
        SilicioNet.init();

        tab.addIcon(EnumSilBlocks.CONTROLLER.getStack(1));
        PROXY.preInit();
    }

    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event)
    {
        SilicioAPI_Impl.clear();
    }
}