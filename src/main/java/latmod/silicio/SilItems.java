package latmod.silicio;

import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleRegistry;
import latmod.silicio.block.BlockAntimatter;
import latmod.silicio.block.BlockBlueGoo;
import latmod.silicio.block.BlockLaserIO;
import latmod.silicio.block.BlockLaserMirrorBox;
import latmod.silicio.block.BlockModuleSocket;
import latmod.silicio.block.BlockSilBlocks;
import latmod.silicio.block.BlockSilMachines;
import latmod.silicio.block.BlockTurret;
import latmod.silicio.item.ItemModule;
import latmod.silicio.item.ItemSilMaterials;

import java.util.HashMap;
import java.util.Map;

public class SilItems
{
	public static BlockSilBlocks b_blocks;
	public static BlockSilMachines b_machines;
	public static BlockBlueGoo b_blue_goo;
	public static BlockAntimatter b_antimatter;
	public static BlockModuleSocket b_module_socket;
	public static BlockLaserMirrorBox b_laser_mirror;
	public static BlockLaserIO b_laser_rx;
	public static BlockLaserIO b_laser_tx;
	public static BlockTurret b_turret;
	
	public static ItemSilMaterials i_mat;
	public static Map<String, ItemModule> i_modules;
	
	public static void init()
	{
		/*
		b_module_copier = new BlockModuleCopier("module_copier").register();
		b_rednet_io = new BlockRedNetIO("rednet_io").register();
		b_computer_io = new BlockComputerIO("computer_io").register();
		
		Silicio.mod.addItem(i_xsuit_helm = new ItemXSuitHelm("xsuit_helm"));
		Silicio.mod.addItem(i_xsuit_body = new ItemXSuitBody("xsuit_body"));
		Silicio.mod.addItem(i_xsuit_legs = new ItemXSuitLegs("xsuit_legs"));
		Silicio.mod.addItem(i_xsuit_boots = new ItemXSuitBoots("xsuit_boots"));
		*/
		
		b_blocks = Silicio.mod.register("blocks", new BlockSilBlocks());
		b_machines = Silicio.mod.register("machines", new BlockSilMachines());
		b_blue_goo = Silicio.mod.register("blue_goo", new BlockBlueGoo());
		b_antimatter = Silicio.mod.register("antimatter_carpet", new BlockAntimatter());
		b_module_socket = Silicio.mod.register("module_socket_block", new BlockModuleSocket());
		b_laser_mirror = Silicio.mod.register("laser_mirror_box", new BlockLaserMirrorBox());
		b_laser_rx = Silicio.mod.register("laser_rx", new BlockLaserIO(true));
		b_laser_tx = Silicio.mod.register("laser_tx", new BlockLaserIO(false));
		b_turret = Silicio.mod.register("turret", new BlockTurret());
		
		i_mat = Silicio.mod.register("mat", new ItemSilMaterials());
		
		ModuleRegistry.init();
		
		i_modules = new HashMap<>();
		for(Module m : ModuleRegistry.modules())
		{
			i_modules.put(m.getID(), Silicio.mod.register("module_" + m.getID(), new ItemModule(m)));
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