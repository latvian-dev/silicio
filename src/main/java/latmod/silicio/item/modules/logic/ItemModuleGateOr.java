package latmod.silicio.item.modules.logic;

import latmod.core.ODItems;
import latmod.silicio.*;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;

public class ItemModuleGateOr extends ItemModuleLogic implements ISignalProvider
{
	public ItemModuleGateOr(String s)
	{
		super(s);
		
		channelNames[0] = "Input 1";
		channelNames[1] = "Input 1";
		channelNames[2] = "Input 1";
		channelNames[3] = "Input 1";
		channelNames[4] = "Output";
	}
	
	public int getChannelCount()
	{ return 5; }
	
	public IOType getChannelType(int c)
	{ return c == 4 ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.EMPTY, SilMat.SILICON, ODItems.REDSTONE, ODItems.REDSTONE);
	}
	
	public void provideSignals(CircuitBoard cb, int MID)
	{
	}
}