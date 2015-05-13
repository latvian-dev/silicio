package latmod.silicio.item.modules.logic;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSNum;
import latmod.silicio.item.modules.events.EventProvideSignals;
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
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getChannelType(int c)
	{ return IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.LOGIC, Items.clock, Blocks.redstone_torch);
	}
	
	public void provideSignals(EventProvideSignals e)
	{
		int t = cs_timer.get(e.item());
		if(e.isTick(t)) e.setEnabled(0);
	}
}