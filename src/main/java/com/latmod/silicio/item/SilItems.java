package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.IMaterial;
import com.feed_the_beast.ftbl.api.item.MaterialItem;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.LMRecipes;
import com.feed_the_beast.ftbl.util.EnumDyeColorHelper;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.modules.ModuleTimer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class SilItems
{
    public static final ItemSilMaterials MAT = Silicio.register("mat", new ItemSilMaterials());

    public static final ItemXSuit XSUIT_HELM = Silicio.register("xsuit_helm", new ItemXSuit(EntityEquipmentSlot.HEAD));
    public static final ItemXSuit XSUIT_BODY = Silicio.register("xsuit_body", new ItemXSuit(EntityEquipmentSlot.CHEST));

    public static final IMaterial BLUE_GOO = new MaterialItem(3, "blue_goo");
    public static final IMaterial LASER_LENS = new MaterialItem(4, "laser_lens");
    public static final IMaterial XSUIT_PLATE = new MaterialItem(5, "xsuit_plate");
    public static final IMaterial ANTIMATTER = new MaterialItem(6, "antimatter");

    public static final IMaterial ELEMITE_INGOT = new MaterialItem(10, "elemite_ingot");
    public static final IMaterial ELEMITE_NUGGET = new MaterialItem(11, "elemite_nugget");
    public static final IMaterial ELEMITE_DUST = new MaterialItem(12, "elemite_dust");

    public static final String ORE_ELEMITE_INGOT = "ingotElemite";
    public static final String ORE_ELEMITE_NUGGET = "nuggetElemite";
    public static final String ORE_ELEMITE_DUST = "dustElemite";

    public static final IMaterial WIRE = new MaterialItem(20, "wire");
    public static final IMaterial RESISTOR = new MaterialItem(21, "resistor");
    public static final IMaterial CAPACITOR = new MaterialItem(22, "capacitor");
    public static final IMaterial DIODE = new MaterialItem(23, "diode");
    public static final IMaterial TRANSISTOR = new MaterialItem(24, "transistor");
    public static final IMaterial CHIP = new MaterialItem(25, "chip");
    public static final IMaterial PROCESSOR = new MaterialItem(26, "processor");
    public static final IMaterial CIRCUIT = new MaterialItem(27, "circuit");
    public static final IMaterial CIRCUIT_WIFI = new MaterialItem(28, "circuit_wifi");
    public static final IMaterial LED_RED = new MaterialItem(29, "led_red");
    public static final IMaterial LED_GREEN = new MaterialItem(30, "led_green");
    public static final IMaterial LED_BLUE = new MaterialItem(31, "led_blue");
    public static final IMaterial LED_RGB = new MaterialItem(32, "led_rgb");
    public static final IMaterial LED_MATRIX = new MaterialItem(33, "led_matrix");

    public static final IMaterial MODULE_EMPTY = new MaterialItem(60, "module_empty");
    public static final IMaterial MODULE_INPUT = new MaterialItem(61, "module_input");
    public static final IMaterial MODULE_OUTPUT = new MaterialItem(62, "module_output");
    public static final IMaterial MODULE_LOGIC = new MaterialItem(63, "module_logic");

    public static class Modules
    {
        private static final List<ItemModule> moduleItems = new ArrayList<>();

        //public static final ItemModule COMMAND_BLOCK;
        //public static final ItemModule LIGHT_SENSOR;
        //public static final ItemModule SIGN_OUT;
        public static final ItemModule CHAT_OUT = register("chat_out", new ModuleTimer());
        public static final ItemModule TIMER = register("timer", new ModuleTimer());
        //public static final ItemModule SEQUENCER;
        //public static final ItemModule CRAFTING;

        //public static final ItemModule RS_IN;
        //public static final ItemModule RS_OUT;
        //public static final ItemModule ENERGY_IN;
        //public static final ItemModule ENERGY_OUT;

        //public static final ItemModule ITEM_STORAGE;
        //public static final ItemModule ITEM_IN;
        //public static final ItemModule ITEM_OUT;

        //public static final ItemModule FLUID_STORAGE;
        //public static final ItemModule FLUID_IN;
        //public static final ItemModule FLUID_OUT;

        //public static final ItemModule GATE_NOT;
        //public static final ItemModule GATE_AND;
        //public static final ItemModule GATE_OR;
        //public static final ItemModule GATE_XOR;

        private static ItemModule register(String s, IModule m)
        {
            ItemModule im = new ItemModule(m);
            moduleItems.add(im);
            return Silicio.register("module_" + s, im);
        }

        public static void init()
        {
        }
    }

    public static void init()
    {
        MAT.addAll(SilItems.class);

        OreDictionary.registerOre(SilItems.ORE_ELEMITE_DUST, SilItems.ELEMITE_DUST.getStack(1));
        OreDictionary.registerOre(SilItems.ORE_ELEMITE_INGOT, SilItems.ELEMITE_INGOT.getStack(1));
        OreDictionary.registerOre(SilItems.ORE_ELEMITE_NUGGET, SilItems.ELEMITE_NUGGET.getStack(1));

        Modules.init();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        MAT.loadModels();

        for(ItemModule m : Modules.moduleItems)
        {
            m.addDefaultModel();
        }
    }

    public static void loadRecipes()
    {
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.BLUE_GOO.getStack(1), ODItems.SLIMEBALL, ODItems.LAPIS, ODItems.IRON);
        LMRecipes.INSTANCE.addRecipe(SilItems.XSUIT_PLATE.getStack(1), "EEE", "ESE", "EEE", 'E', SilItems.ORE_ELEMITE_INGOT, 'S', SilItems.ANTIMATTER);

        LMRecipes.INSTANCE.addRecipe(SilItems.ELEMITE_INGOT.getStack(1), "EEE", "EEE", "EEE", 'E', SilItems.ORE_ELEMITE_NUGGET);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.ELEMITE_NUGGET.getStack(9), SilItems.ORE_ELEMITE_INGOT);

        LMRecipes.INSTANCE.addSmelting(SilItems.ELEMITE_INGOT.getStack(1), SilItems.ELEMITE_DUST.getStack(1));
        LMRecipes.INSTANCE.addSmelting(SilItems.ELEMITE_INGOT.getStack(1), SilItems.BLUE_GOO.getStack(1));

        LMRecipes.INSTANCE.addRecipe(SilItems.WIRE.getStack(8), "WWW", "NNN", "WWW", 'N', SilItems.ORE_ELEMITE_NUGGET, 'W', ODItems.STRING);
        LMRecipes.INSTANCE.addRecipe(SilItems.RESISTOR.getStack(4), "WCW", 'C', Items.BRICK, 'W', SilItems.WIRE);
        LMRecipes.INSTANCE.addRecipe(SilItems.CAPACITOR.getStack(4), "WCW", 'C', Items.CLAY_BALL, 'W', SilItems.WIRE);
        LMRecipes.INSTANCE.addRecipe(SilItems.DIODE.getStack(4), "WCW", 'C', ODItems.SILICON, 'W', SilItems.WIRE);
        LMRecipes.INSTANCE.addRecipe(SilItems.TRANSISTOR.getStack(3), "DDD", "WWW", 'D', SilItems.DIODE, 'W', SilItems.WIRE);
        LMRecipes.INSTANCE.addRecipe(SilItems.CHIP.getStack(1), "TT", "TT", "TT", 'T', SilItems.TRANSISTOR);
        LMRecipes.INSTANCE.addRecipe(SilItems.PROCESSOR.getStack(1), "CCC", "CSC", "CCC", 'C', SilItems.CHIP, 'S', ODItems.SILICON);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.CIRCUIT.getStack(1), SilItems.PROCESSOR, SilItems.WIRE, SilItems.RESISTOR, SilItems.CAPACITOR, SilItems.TRANSISTOR, SilItems.PROCESSOR, SilItems.WIRE, SilItems.WIRE, SilItems.ORE_ELEMITE_NUGGET);
        LMRecipes.INSTANCE.addRecipe(SilItems.CIRCUIT_WIFI.getStack(1), "NEN", "ECE", "NEN", 'C', SilItems.CIRCUIT, 'E', Items.ENDER_PEARL, 'N', SilItems.ORE_ELEMITE_NUGGET);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.LED_RED.getStack(3), SilItems.DIODE, EnumDyeColorHelper.get(EnumDyeColor.RED).dyeName, ODItems.GLOWSTONE);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.LED_GREEN.getStack(3), SilItems.DIODE, EnumDyeColorHelper.get(EnumDyeColor.GREEN).dyeName, ODItems.GLOWSTONE);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.LED_BLUE.getStack(3), SilItems.DIODE, EnumDyeColorHelper.get(EnumDyeColor.BLUE).dyeName, ODItems.GLOWSTONE);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.LED_RGB.getStack(3), SilItems.LED_RED, SilItems.LED_GREEN, SilItems.LED_BLUE);
        LMRecipes.INSTANCE.addRecipe(SilItems.LED_MATRIX.getStack(1), "LLL", "LLL", "LLL", 'L', SilItems.LED_RGB);

        LMRecipes.INSTANCE.addRecipe(SilItems.MODULE_EMPTY.getStack(1), "III", "ICI", "III", 'I', ODItems.IRON, 'C', SilItems.PROCESSOR);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.MODULE_INPUT.getStack(1), SilItems.MODULE_EMPTY, EnumDyeColorHelper.get(EnumDyeColor.LIGHT_BLUE).dyeName);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.MODULE_OUTPUT.getStack(1), SilItems.MODULE_EMPTY, EnumDyeColorHelper.get(EnumDyeColor.ORANGE).dyeName);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.MODULE_LOGIC.getStack(1), SilItems.MODULE_EMPTY, ODItems.REDSTONE);

        for(ItemModule m : Modules.moduleItems)
        {
            m.module.addRecipes(new ItemStack(m));
        }
    }
}