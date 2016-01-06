package latmod.silicio.item.modules.logic;

import ftb.lib.item.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.cb.events.EventProvideSignals;
import net.minecraft.item.ItemStack;

public class ItemModuleGateOr extends ItemModuleLogic implements ISignalProvider
{
	public ItemModuleGateOr(String s)
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
		getMod().recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.LOGIC, ODItems.SILICON, ODItems.REDSTONE);
	}
	
	public void provideSignals(EventProvideSignals e)
	{
	}
}