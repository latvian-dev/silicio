package latmod.silicio.item.modules.logic;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSNum;
import latmod.silicio.tile.cb.events.EventProvideSignals;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleSequencer extends ItemModuleLogic implements ISignalProvider
{
	public static final ModuleCSNum cs_timer = new ModuleCSNum(0, "Timer");
	
	public ItemModuleSequencer(String s)
	{
		super(s);
		cs_timer.setValues(20, 1, 48000, 0);
		moduleConfig.add(cs_timer);
		
		channelNames[0] = "Output 1";
		channelNames[1] = "Output 2";
		channelNames[2] = "Output 3";
		channelNames[3] = "Output 4";
	}
	
	public int getChannelCount()
	{ return 4; }
	
	public IOType getChannelType(int c)
	{ return IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " R ", "RTR", " R ", 'T', SilItems.Modules.i_timer, 'R', Blocks.redstone_torch);
	}
	
	public void provideSignals(EventProvideSignals e)
	{
		int t = cs_timer.get(e.item());
		int p = (int) ((e.net.controller.tick / t) % 4L);
		e.setEnabled(p);
	}
}