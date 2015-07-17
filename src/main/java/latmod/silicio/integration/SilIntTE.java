package latmod.silicio.integration;

import latmod.ftbu.core.recipes.*;
import latmod.ftbu.core.util.FastList;
import latmod.silicio.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import cofh.thermalexpansion.block.simple.BlockFrame;
import cofh.thermalexpansion.util.crafting.*;

public class SilIntTE
{
	private static boolean addAllOreItems = true;
	
	public static void onLoaded() throws Exception
	{
		if(SilConfig.General.disableAllCrafting) return;
		
		// Circuit Board //
		
		addSmelter(new ItemStack(SilItems.i_circuit_board), SilMat.CIRCUIT, LMRecipes.size(SilMat.SILICON_ITEM, 8), 1200);
		
		// Materials //
		addSmelter(SilItems.Modules.EMPTY, SilMat.CIRCUIT, new ItemStack(Items.iron_ingot, 8), 12000);
		addSmelter(SilItems.Modules.INPUT, SilItems.Modules.EMPTY, SilMat.LAPIS_CRYSTAL, 1200);
		addSmelter(SilItems.Modules.OUTPUT, SilItems.Modules.EMPTY, SilMat.REDSTONE_CRYSTAL, 1200);
		
		addPulverizer(SilMat.SILICON_DUST, SilMat.SILICON, 2400);
		addPulverizer(SilMat.SILICON_DUST, Blocks.sand, 4800);
		
		addSmelter(SilMat.LAPIS_CRYSTAL, new ItemStack(Items.dye, 4, 4), new ItemStack(Blocks.glass), 4800);
		addSmelter(SilMat.REDSTONE_CRYSTAL, new ItemStack(Items.redstone, 4), new ItemStack(Blocks.glass), 4800);
		
		Silicio.mod.recipes.addRecipe(new ItemStack(SilItems.b_cbcontroller), "SIS", "CFC", "SIS",
				'S', SilMat.SILICON,
				'F', BlockFrame.frameTesseractFull,
				'C', SilItems.b_cbcable,
				'I', SilMat.CIRCUIT);
		
		Silicio.mod.recipes.addRecipe(new ItemStack(SilItems.b_module_copier), " C ", "MFM", "GLG",
				'C', SilMat.CIRCUIT,
				'M', SilItems.Modules.EMPTY,
				'L', SilMat.LAPIS_CRYSTAL,
				'F', BlockFrame.frameMachineBasic,
				'G', "gearTin");
	}
	
	private static void addPulverizer(ItemStack out, Object in, int energy, ItemStack out2, int out2chance)
	{
		FastList<ItemStack> inItems = StackEntry.getItems(in);
		
		if(inItems.size() > 0)
		{
			if(addAllOreItems) for(int i = 0; i < inItems.size(); i++)
				PulverizerManager.addRecipe(energy, inItems.get(i), out, out2, out2chance, true);
			else
				PulverizerManager.addRecipe(energy, inItems.get(0), out, out2, out2chance, true);
		}
	}
	
	private static void addPulverizer(ItemStack out, Object in, int energy)
	{ addPulverizer(out, in, energy, null, 0); }
	
	private static void addSmelter(ItemStack out, ItemStack in1, ItemStack in2, int energy)
	{ SmelterManager.addRecipe(energy, in2, in1, out); }
}