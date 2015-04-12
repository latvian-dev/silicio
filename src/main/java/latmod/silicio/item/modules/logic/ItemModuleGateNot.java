package latmod.silicio.item.modules.logic;

import latmod.silicio.*;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleGateNot extends ItemModuleLogic implements ISignalProvider
{
	public ItemModuleGateNot(String s)
	{
		super(s);
	}
	
	public int getChannelCount()
	{ return 2; }
	
	public IOType getChannelType(int c)
	{ return c == 0 ? IOType.INPUT : IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.EMPTY, SilMat.SILICON, Blocks.redstone_torch);
	}
	
	public void provideSignals(ItemStack is, CircuitBoard t)
	{ if(!getChannel(is, t, 0).isEnabled()) getChannel(is, t, 1).enable(); }
}