package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.MaterialItem;
import com.latmod.silicio.Silicio;

public class SilItems
{
    public static final ItemSilMaterials MAT = Silicio.mod.register("mat", new ItemSilMaterials());
    public static final ItemModule MODULE = Silicio.mod.register("module", new ItemModule());
    
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

    public static void init()
    {
    }
    
	/*
    public static class Modules
	{
		public static ItemModule i_command_block;
		public static ItemModule i_light_sensor;
		public static ItemModule i_sign_out;
		public static ItemModule i_chat_out;
		public static ItemModule i_crafting;
		public static ItemModule i_painter;
		
		public static ItemModule i_rs_in;
		public static ItemModule i_rs_out;
		public static ItemModule i_energy_in;
		public static ItemModule i_energy_out;
		
		public static ItemModule i_item_storage;
		public static ItemModule i_item_in;
		public static ItemModule i_item_out;
		
		public static ItemModule i_fluid_storage;
		public static ItemModule i_fluid_in;
		public static ItemModule i_fluid_out;
		
		public static ItemModule i_gate_not;
		public static ItemModule i_gate_and;
		public static ItemModule i_gate_or;
		public static ItemModule i_gate_xor;
		
		public static ItemModule i_timer;
		public static ItemModule i_sequencer;
		
		public static void init()
		{
			i_command_block = new ItemModuleCommandBlock("command_block").register();
			i_light_sensor = new ItemModuleLightSensor("light_sensor").register();
			i_sign_out = new ItemModuleSignOutput("sign_out").register();
			i_chat_out = new ItemModuleChatOutput("chat_out").register();
			i_crafting = new ItemModuleCrafting("crafting").register();
			i_painter = new ItemModulePainter("painter").register();
			
			i_rs_in = new ItemModuleRedstoneIn("rs_in").register();
			i_rs_out = new ItemModuleRedstoneOut("rs_out").register();
			i_energy_in = new ItemModuleEnergyInput("energy_in").register();
			i_energy_out = new ItemModuleEnergyOutput("energy_out").register();
			
			i_item_storage = new ItemModuleItemStorage("item_storage").register();
			i_item_in = new ItemModuleItemInput("item_in").register();
			i_item_out = new ItemModuleItemOutput("item_out").register();
			
			i_fluid_storage = new ItemModuleFluidStorage("fluid_storage").register();
			i_fluid_in = new ItemModuleFluidInput("fluid_in").register();
			i_fluid_out = new ItemModuleFluidOutput("fluid_out").register();
			
			i_gate_not = new ItemModuleGateNot("gate_not").register();
			i_gate_and = new ItemModuleGateAnd("gate_and").register();
			i_gate_or = new ItemModuleGateOr("gate_or").register();
			i_gate_xor = new ItemModuleGateXOr("gate_xor").register();
			
			i_timer = new ItemModuleTimer("timer").register();
			i_sequencer = new ItemModuleSequencer("sequencer").register();
		}
	}
	*/
}