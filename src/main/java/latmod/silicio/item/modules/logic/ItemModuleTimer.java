package latmod.silicio.item.modules.logic;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSNum;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public class ItemModuleTimer extends ItemModuleLogic implements ISignalProvider
{
	public static final ModuleCSNum cs_timer = new ModuleCSNum(0, "Timer");
	
	public ItemModuleTimer(String s)
	{
		super(s);
		cs_timer.setValues(20, 1, 48000, 0);
		moduleConfig.add(cs_timer);
		
		channelNames[0] = "Output";
		channelNames[1] = "Input";
	}
	
	public int getChannelCount()
	{ return 2; }
	
	public IOType getChannelType(int c)
	{ return c == 0 ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.LOGIC, Items.clock, Blocks.redstone_torch);
	}
	
	public void provideSignals(CircuitBoard cb, int MID)
	{
		int t = cs_timer.get(cb.items[MID]);
		if(cb.tick % t == 0L && !getChannel(cb, MID, 1).isEnabled())
			getChannel(cb, MID, 0).enable();
	}
}