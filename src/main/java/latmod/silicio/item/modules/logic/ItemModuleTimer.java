package latmod.silicio.item.modules.logic;

import latmod.core.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSInt;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleTimer extends ItemModuleLogic implements ISignalProvider
{
	public static final ModuleCSInt cs_timer = new ModuleCSInt(0, "Timer");
	
	public ItemModuleTimer(String s)
	{
		super(s);
		
		cs_timer.defaultValue = 20;
		cs_timer.min = 1;
		cs_timer.max = 48000;
		moduleConfig.add(cs_timer);
	}
	
	public int getChannelCount()
	{ return 2; }
	
	public IOType getChannelType(int c)
	{ return c == 0 ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.EMPTY, ODItems.QUARTZ, Blocks.redstone_torch);
	}
	
	public void provideSignals(CircuitBoard cb, int MID)
	{
		int t = cs_timer.get(cb.items[MID]);
		if(cb.tick % t == 0L && !getChannel(cb, MID, 1).isEnabled())
			getChannel(cb, MID, 0).enable();
	}
}