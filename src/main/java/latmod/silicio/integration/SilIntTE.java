package latmod.silicio.integration;

import latmod.ftbu.item.MaterialItem;
import latmod.ftbu.recipes.StackArray;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.silicio.SilItems;
import latmod.silicio.config.SilConfigGeneral;
import latmod.silicio.item.ItemMaterialsSil;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class SilIntTE
{
	private static boolean addAllOreItems = true;
	
	public static void onLoaded() throws Exception
	{
		if(SilConfigGeneral.disableAllCrafting.get()) return;
		
		// Circuit Board //
		
		addSmelter(new ItemStack(SilItems.i_circuit_board), ItemMaterialsSil.CIRCUIT, ItemMaterialsSil.SILICON_GEM.getStack(8), 1200);
		
		// Materials //
		addSmelter(SilItems.Modules.EMPTY, ItemMaterialsSil.CIRCUIT, new ItemStack(Items.iron_ingot, 8), 12000);
		addSmelter(SilItems.Modules.INPUT, SilItems.Modules.EMPTY, ItemMaterialsSil.BLUE_CRYSTAL, 1200);
		addSmelter(SilItems.Modules.OUTPUT, SilItems.Modules.EMPTY, ItemMaterialsSil.RED_CRYSTAL, 1200);
		
		addPulverizer(ItemMaterialsSil.SILICON_GEM, Blocks.sand, 2400);
		
		addSmelter(ItemMaterialsSil.BLUE_CRYSTAL, ItemMaterialsLB.DUST_GLOWIUM_B, new ItemStack(Blocks.glass), 4800);
		addSmelter(ItemMaterialsSil.RED_CRYSTAL, ItemMaterialsLB.DUST_GLOWIUM_R, new ItemStack(Blocks.glass), 4800);

		/*
		Silicio.mod.recipes.addRecipe(new ItemStack(SilItems.b_cbcontroller), "SIS", "CFC", "SIS",
				'S', ODItems.SILICON,
				'F', BlockFrame.frameTesseractFull,
				'C', SilItems.b_cbcable,
				'I', ItemMaterialsSil.CIRCUIT);
		
		Silicio.mod.recipes.addRecipe(new ItemStack(SilItems.b_module_copier), " C ", "MFM", "GLG",
				'C', ItemMaterialsSil.CIRCUIT,
				'M', SilItems.Modules.EMPTY,
				'L', ItemMaterialsSil.BLUE_CRYSTAL,
				'F', BlockFrame.frameMachineBasic,
				'G', "gearTin");
				*/
	}
	
	private static void addPulverizer(ItemStack out, Object in, int energy, ItemStack out2, int out2chance)
	{
		Collection<ItemStack> inItems = StackArray.getItems(in);
		
		if(!inItems.isEmpty())
		{
			/*
			if(addAllOreItems) for(int i = 0; i < inItems.size(); i++)
				PulverizerManager.addRecipe(energy, inItems.get(i), out, out2, out2chance, true);
			else
				PulverizerManager.addRecipe(energy, inItems.get(0), out, out2, out2chance, true);
				*/
		}
	}
	
	private static ItemStack getStack(Object o)
	{
		if(o == null) return null;
		return (o instanceof MaterialItem) ? ((MaterialItem) o).getStack() : (ItemStack) o;
	}
	
	private static void addPulverizer(Object out, Object in, int energy)
	{ addPulverizer(getStack(out), in, energy, null, 0); }
	
	private static void addSmelter(Object out, Object in1, Object in2, int energy)
	//{ SmelterManager.addRecipe(energy, getStack(in2), getStack(in1), getStack(out)); }
	{ }
}