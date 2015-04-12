package latmod.silicio.item.modules.logic;

import latmod.core.ODItems;
import latmod.silicio.*;
import latmod.silicio.item.modules.IOType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleGateXOr extends ItemModuleLogic
{
	public ItemModuleGateXOr(String s)
	{
		super(s);
	}
	
	public int getChannelCount()
	{ return 5; }
	
	public IOType getChannelType(int c)
	{ return c == 4 ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.EMPTY, SilMat.SILICON, Blocks.redstone_torch, ODItems.REDSTONE);
	}
}