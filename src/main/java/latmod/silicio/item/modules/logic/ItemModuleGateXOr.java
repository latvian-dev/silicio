package latmod.silicio.item.modules.logic;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleGateXOr extends ItemModuleLogic implements ISignalProvider
{
	public ItemModuleGateXOr(String s)
	{
		super(s);
		
		channelNames[0] = "Input 1";
		channelNames[1] = "Input 2";
		channelNames[2] = "Input 3";
		channelNames[3] = "Input 4";
		channelNames[4] = "Output";
	}
	
	public int getChannelCount()
	{ return 5; }
	
	public IOType getChannelType(int c)
	{ return c == 4 ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.i_gate_or, Blocks.redstone_torch);
	}
	
	public void provideSignals(CircuitBoard cb, int MID, boolean pre)
	{
		if(pre) return;
	}
}