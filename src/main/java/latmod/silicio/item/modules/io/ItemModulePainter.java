package latmod.silicio.item.modules.io;

import latmod.core.tile.IPaintable.Paint;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSItem;
import latmod.silicio.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModulePainter extends ItemModuleIO implements IToggable
{
	public ItemModulePainter(String s)
	{
		super(s);
		
		moduleConfig.add(new ModuleCSItem()
		{
			public boolean isValid(ItemStack o)
			{ return true; }
		});
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
	
	public void onChannelToggled(ItemStack is, CircuitBoard t, CBChannel c)
	{
		if(t.cable.hasCover && c == getChannel(is, t, 0))
		{
			Paint p = null;
			
			if(c.isEnabled())
				p = new Paint(Blocks.wool, 5);
			else
				p = new Paint(Blocks.wool, 14);
			
			if(p != null)
			{
				t.cable.paint[t.side.ordinal()] = p;
				t.cable.markDirty();
			}
		}
	}
}