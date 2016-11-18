package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.lib.item.ItemMaterialsLM;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.modules.ModuleChatOutput;
import com.latmod.silicio.modules.ModuleSequencer;
import com.latmod.silicio.modules.ModuleTimer;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class SilItems
{
    public static final ItemMaterialsLM MAT = new ItemMaterialsLM();
    public static final ItemIDCard ID_CARD = new ItemIDCard();
    public static final ItemModule MODULE = new ItemModule();
    public static final ItemMultiTool MULTITOOL = new ItemMultiTool();

    //public static final ItemXSuitBelt XSUIT_BELT = Silicio.register("xsuit_belt", new ItemXSuitBelt());
    //public static final ItemXSuitVisor XSUIT_VISOR = Silicio.register("xsuit_visor", new ItemXSuitVisor());

    public static final String ORE_ELEMITE_INGOT = "ingotElemite";
    public static final String ORE_ELEMITE_NUGGET = "nuggetElemite";
    public static final String ORE_ELEMITE_DUST = "dustElemite";

    public static class Modules
    {
        //public static final IModule COMMAND_BLOCK;
        //public static final IModule LIGHT_SENSOR;
        //public static final IModule SIGN_OUT;

        public static final IModule CHAT_OUT = new ModuleChatOutput();
        public static final IModule TIMER = new ModuleTimer();
        public static final IModule SEQUENCER = new ModuleSequencer(4);

        //public static final IModule CRAFTING;

        //public static final IModule RS_IN;
        //public static final IModule RS_OUT;
        //public static final IModule ENERGY_IN;
        //public static final IModule ENERGY_OUT;

        //public static final IModule ITEM_STORAGE;
        //public static final IModule ITEM_IN;
        //public static final IModule ITEM_OUT;

        //public static final IModule FLUID_STORAGE;
        //public static final IModule FLUID_IN;
        //public static final IModule FLUID_OUT;

        //public static final IModule GATE_NOT;
        //public static final IModule GATE_AND;
        //public static final IModule GATE_OR;
        //public static final IModule GATE_XOR;
    }

    public static void init()
    {
        Silicio.register("mat", MAT);
        Silicio.register("id_card", ID_CARD);
        Silicio.register("module", MODULE);
        Silicio.register("multitool", MULTITOOL);

        MAT.setCreativeTab(Silicio.INST.tab);
        MAT.setFolder("materials");
        MAT.addAll(Arrays.asList(EnumMat.values()));
        MAT.setDefaultMaterial(EnumMat.BLUE_GOO);

        OreDictionary.registerOre(ORE_ELEMITE_DUST, EnumMat.ELEMITE_DUST.getStack(1));
        OreDictionary.registerOre(ORE_ELEMITE_INGOT, EnumMat.ELEMITE_INGOT.getStack(1));
        OreDictionary.registerOre(ORE_ELEMITE_NUGGET, EnumMat.ELEMITE_NUGGET.getStack(1));
    }
}