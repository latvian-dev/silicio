package latmod.silicio.item.modules.logic;

import latmod.ftbu.core.inv.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.cb.events.EventProvideSignals;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleGateNot extends ItemModuleLogic implements ISignalProvider
{
	public ItemModuleGateNot(String s)
	{
		super(s);
		
		channelNames[0] = "Input";
		channelNames[1] = "Output";
	}
	
	public int getChannelCount()
	{ return 2; }
	
	public IOType getChannelType(int c)
	{ return c == 0 ? IOType.INPUT : IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.LOGIC, ODItems.SILICON, Blocks.redstone_torch);
	}
	
	public void provideSignals(EventProvideSignals e)
	{
		if(!e.isEnabled(0, -1, true)) e.setEnabled(1);
	}
}