package latmod.silicio;

import latmod.core.recipes.*;
import latmod.core.util.FastList;
import net.minecraft.item.ItemStack;
import cofh.thermalexpansion.util.crafting.*;

public class SilRecipes extends LMRecipes
{
	public static boolean addAllOreItems = true;
	
	public static void addPulverizer(ItemStack out, Object in, int energy, ItemStack out2, int out2chance)
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
	
	public static void addPulverizer(ItemStack out, Object in, int energy)
	{ addPulverizer(out, in, energy, null, 0); }
	
	public static void addSmelter(ItemStack out, ItemStack in1, ItemStack in2, int energy)
	{ SmelterManager.addRecipe(energy, in2, in1, out); }
}