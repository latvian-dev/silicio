package latmod.silicio;

import latmod.silicio.api.modules.*;
import latmod.silicio.block.*;
import latmod.silicio.item.*;

import java.util.*;

public class SilItems
{
	public static BlockSimpleBlocks b_blocks;
	public static BlockSiliconBlock b_silicon_block;
	public static BlockSiliconFrame b_silicon_frame;
	public static BlockSiliconGlass b_glass;
	public static BlockBlueGoo b_blue_goo;
	public static BlockReactorCore b_reactor_core;
	public static BlockCable b_cable;
	public static BlockModuleSocket b_module_socket;
	public static BlockCBController b_cb_controller;
	
	public static ItemSilMaterials i_mat;
	public static Map<String, ItemModule> i_modules;
	
	public static void init()
	{
		/*b_cbcable = new BlockCBCable("cable").register();
		b_cbcontroller = new BlockCBController("controller").register();
		b_module_copier = new BlockModuleCopier("module_copier").register();
		b_rednet_io = new BlockRedNetIO("rednet_io").register();
		b_computer_io = new BlockComputerIO("computer_io").register();
		
		i_mat = new ItemMaterialsSil("materials").register();
		i_circuit_board = new ItemCircuitBoard("circuit_board").register();
		
		Silicio.mod.addItem(i_xsuit_helm = new ItemXSuitHelm("xsuit_helm"));
		Silicio.mod.addItem(i_xsuit_body = new ItemXSuitBody("xsuit_body"));
		Silicio.mod.addItem(i_xsuit_legs = new ItemXSuitLegs("xsuit_legs"));
		Silicio.mod.addItem(i_xsuit_boots = new ItemXSuitBoots("xsuit_boots"));
		
		*/
		
		Silicio.mod.addItem(b_blocks = new BlockSimpleBlocks("blocks"));
		Silicio.mod.addItem(b_silicon_block = new BlockSiliconBlock("silicon_block"));
		Silicio.mod.addItem(b_silicon_frame = new BlockSiliconFrame("silicon_frame"));
		Silicio.mod.addItem(b_glass = new BlockSiliconGlass("silicon_glass"));
		Silicio.mod.addItem(b_blue_goo = new BlockBlueGoo("blue_goo"));
		Silicio.mod.addItem(b_reactor_core = new BlockReactorCore("reactor_core"));
		Silicio.mod.addItem(b_cable = new BlockCable("cable"));
		Silicio.mod.addItem(b_module_socket = new BlockModuleSocket("module_socket_block"));
		Silicio.mod.addItem(b_cb_controller = new BlockCBController("controller"));
		
		Silicio.mod.addItem(i_mat = new ItemSilMaterials("mat"));
		
		ModuleRegistry.init();
		
		i_modules = new HashMap<>();
		for(Module m : ModuleRegistry.modules())
		{
			ItemModule module = new ItemModule(m);
			Silicio.mod.addItem(module);
			i_modules.put(m.getID(), module);
		}
		
		//Modules.init();
	}
	
	/*
	public static class Modules
	{
		public static MaterialItem EMPTY;
		public static MaterialItem INPUT;
		public static MaterialItem OUTPUT;
		public static MaterialItem LOGIC;
		
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