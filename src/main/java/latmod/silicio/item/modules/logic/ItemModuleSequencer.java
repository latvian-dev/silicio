package latmod.silicio.item.modules.logic;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSNum;
import latmod.silicio.tile.*;
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
		channelNames[4] = "Input";
	}
	
	public int getChannelCount()
	{ return 5; }
	
	public IOType getChannelType(int c)
	{ return c == 4 ? IOType.INPUT : IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " R ", "RTR", " R ", 
				'T', SilItems.Modules.i_timer,
				'R', Blocks.redstone_torch);
	}
	
	public void provideSignals(CircuitBoard cb, int MID, boolean pre)
	{
		if(pre) return;
		
		CBChannel c4 = getChannel(cb, MID, 4);
		if(c4 != CBChannel.NONE && !c4.isEnabled()) return;
		
		int t = cs_timer.get(cb.items[MID]);
		int p = (int)((cb.tick / t) % 4L);
		getChannel(cb, MID, p).enable();
	}
}