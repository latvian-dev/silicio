package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.MaterialItem;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.modules.ModuleTimer;

public class SilItems
{
    public static final ItemSilMaterials MAT = Silicio.mod.register("mat", new ItemSilMaterials());
    
	/*
    public static final ItemXSuitHelm XSUIT_HELM = Silicio.mod.addItem("xsuit_helm", new ItemXSuitHelm());
	public static final ItemXSuitBody XSUIT_BODY = Silicio.mod.addItem("xsuit_body", new ItemXSuitBody());
	public static final ItemXSuitLegs XSUIT_LEGS = Silicio.mod.addItem("xsuit_legs", new ItemXSuitLegs());
	public static final ItemXSuitBoots XSUIT_BOOTS = Silicio.mod.addItem("xsuit_boots", new ItemXSuitBoots());
	*/

    public static final MaterialItem BLUE_GOO = new MaterialItem(3, "blue_goo");
    public static final MaterialItem LASER_LENS = new MaterialItem(4, "laser_lens");
    public static final MaterialItem XSUIT_PLATE = new MaterialItem(5, "xsuit_plate");
    public static final MaterialItem ANTIMATTER = new MaterialItem(6, "antimatter");

    public static final MaterialItem ELEMITE_INGOT = new MaterialItem(10, "elemite_ingot");
    public static final MaterialItem ELEMITE_NUGGET = new MaterialItem(11, "elemite_nugget");
    public static final MaterialItem ELEMITE_DUST = new MaterialItem(12, "elemite_dust");

    public static final String ORE_ELEMITE_INGOT = "ingotElemite";
    public static final String ORE_ELEMITE_NUGGET = "nuggetElemite";
    public static final String ORE_ELEMITE_DUST = "dustElemite";

    public static final MaterialItem WIRE = new MaterialItem(20, "wire");
    public static final MaterialItem RESISTOR = new MaterialItem(21, "resistor");
    public static final MaterialItem CAPACITOR = new MaterialItem(22, "capacitor");
    public static final MaterialItem DIODE = new MaterialItem(23, "diode");
    public static final MaterialItem TRANSISTOR = new MaterialItem(24, "transistor");
    public static final MaterialItem CHIP = new MaterialItem(25, "chip");
    public static final MaterialItem PROCESSOR = new MaterialItem(26, "processor");
    public static final MaterialItem CIRCUIT = new MaterialItem(27, "circuit");
    public static final MaterialItem CIRCUIT_WIFI = new MaterialItem(28, "circuit_wifi");
    public static final MaterialItem LED_RED = new MaterialItem(29, "led_red");
    public static final MaterialItem LED_GREEN = new MaterialItem(30, "led_green");
    public static final MaterialItem LED_BLUE = new MaterialItem(31, "led_blue");
    public static final MaterialItem LED_RGB = new MaterialItem(32, "led_rgb");
    public static final MaterialItem LED_MATRIX = new MaterialItem(33, "led_matrix");

    public static final MaterialItem MODULE_EMPTY = new MaterialItem(60, "module_empty");
    public static final MaterialItem MODULE_INPUT = new MaterialItem(61, "module_input");
    public static final MaterialItem MODULE_OUTPUT = new MaterialItem(62, "module_output");
    public static final MaterialItem MODULE_LOGIC = new MaterialItem(63, "module_logic");

    public static class Modules
    {
        //public static final ItemModule COMMAND_BLOCK;
        //public static final ItemModule LIGHT_SENSOR;
        //public static final ItemModule SIGN_OUT;
        public static final ItemModule CHAT_OUT = register("chat_out", new ModuleTimer());
        public static final ItemModule TIMER = register("timer", new ModuleTimer());
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
            return Silicio.mod.register("module_" + s, new ItemModule(m));
        }
        //public static final ItemModule SEQUENCER;

        public static void init()
        {
        }
    }

    public static void init()
    {
        Modules.init();
    }
}