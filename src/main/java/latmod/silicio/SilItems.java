package latmod.silicio;

import latmod.silicio.block.*;
import latmod.silicio.item.*;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.item.modules.io.*;
import latmod.silicio.item.modules.logic.*;
import net.minecraft.item.ItemStack;

public class SilItems
{
	public static BlockCBCable b_cbcable;
	public static BlockCBController b_cbcontroller;
	
	public static ItemMaterialsSil i_mat;
	public static ItemCircuitBoard i_circuit_board;
	
	public static void init()
	{
		b_cbcable = new BlockCBCable("cable").register();
		b_cbcontroller = new BlockCBController("controller").register();
		
		i_mat = new ItemMaterialsSil("materials").register();
		i_circuit_board = new ItemCircuitBoard("circuitBoard").register();
		
		Modules.init();
	}
	
	public static class Modules
	{
		public static ItemStack EMPTY;
		public static ItemStack INPUT;
		public static ItemStack OUTPUT;
		
		public static ItemModule i_command_block;
		public static ItemModule i_light_sensor;
		public static ItemModule i_sign_out;
		public static ItemModule i_chat_out;
		public static ItemModule i_crafting;
		public static ItemModule i_painter;
		
		public static ItemModule i_redstone_in;
		public static ItemModule i_redstone_out;
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
		
		public static void init()
		{
			i_command_block = new ItemModuleCommandBlock("commandBlock").register();
			i_light_sensor = new ItemModuleLightSensor("lightSensor").register();
			i_sign_out = new ItemModuleSignOutput("signOut").register();
			i_chat_out = new ItemModuleChatOutput("chatOut").register();
			i_crafting = new ItemModuleCrafting("crafting").register();
			i_painter = new ItemModulePainter("painter").register();
			
			i_redstone_in = new ItemModuleRedstoneIn("redstoneIn").register();
			i_redstone_out = new ItemModuleRedstoneOut("redstoneOut").register();
			i_energy_in = new ItemModuleEnergyInput("energyIn").register();
			i_energy_out = new ItemModuleEnergyOutput("energyOut").register();
			
			i_item_storage = new ItemModuleItemStorage("itemStorage").register();
			i_item_in = new ItemModuleItemInput("itemInput").register();
			i_item_out = new ItemModuleItemOutput("itemOutput").register();
			
			i_fluid_storage = new ItemModuleFluidStorage("fluidStorage").register();
			i_fluid_in = new ItemModuleFluidInput("fluidInput").register();
			i_fluid_out = new ItemModuleFluidOutput("fluidOutput").register();
			
			i_gate_not = new ItemModuleGateNot("gateNot").register();
			i_gate_and = new ItemModuleGateAnd("gateAnd").register();
			i_gate_or = new ItemModuleGateOr("gateOr").register();
			i_gate_xor = new ItemModuleGateXOr("gateXOr").register();
		}
	}
}