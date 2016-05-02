package latmod.silicio;

import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleRegistry;
import latmod.silicio.item.ItemModule;
import latmod.silicio.item.ItemSilMaterials;

import java.util.HashMap;
import java.util.Map;

public class SilItems
{
	public static final ItemSilMaterials MAT = Silicio.mod.register("mat", new ItemSilMaterials());
	public static final Map<String, ItemModule> i_modules;
	
	/*
	public static final ItemXSuitHelm XSUIT_HELM = Silicio.mod.addItem("xsuit_helm", new ItemXSuitHelm());
	public static final ItemXSuitBody XSUIT_BODY = Silicio.mod.addItem("xsuit_body", new ItemXSuitBody());
	public static final ItemXSuitLegs XSUIT_LEGS = Silicio.mod.addItem("xsuit_legs", new ItemXSuitLegs());
	public static final ItemXSuitBoots XSUIT_BOOTS = Silicio.mod.addItem("xsuit_boots", new ItemXSuitBoots());
	*/
	
	static
	{
		ModuleRegistry.init();
		
		i_modules = new HashMap<>();
		
		for(Module m : ModuleRegistry.modules())
		{
			i_modules.put(m.getID(), Silicio.mod.register("module_" + m.getID(), new ItemModule(m)));
		}
	}
	
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