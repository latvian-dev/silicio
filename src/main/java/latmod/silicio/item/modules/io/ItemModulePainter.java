package latmod.silicio.item.modules.io;

import latmod.core.tile.IPaintable.Paint;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;

public class ItemModulePainter extends ItemModuleIO implements IToggable
{
	public static final ModuleCSItem cs_paint = new ModuleCSItem(0, "Paint")
	{
		public boolean isValid(ItemStack is)
		{ return is == null || (is.getItem() instanceof ItemBlock && !Block.getBlockFromItem(is.getItem()).hasTileEntity(is.getItemDamage())); }
	};
	
	public static final ModuleCSBool cs_paint_all = new ModuleCSBool(1, "Paint all block");
	
	public ItemModulePainter(String s)
	{
		super(s);
		
		cs_paint.defaultItem = new ItemStack(Blocks.planks, 1, 0);
		moduleConfig.add(cs_paint);
		
		moduleConfig.add(cs_paint_all);
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
	
	public void onChannelToggled(CircuitBoard cb, int MID, CBChannel c)
	{
		if(cb.cable.hasCover && c.isEnabled() && c == getChannel(cb, MID, 0))
		{
			Paint p = null;
			
			ItemStack isp = cs_paint.get(cb.items[MID]);
			
			if(isp != null && isp.getItem() instanceof ItemBlock)
				p = new Paint(Block.getBlockFromItem(isp.getItem()), isp.getItemDamage());
			
			if(cs_paint_all.get(cb.items[MID]))
			{ for(int i = 0; i < 6; i++) cb.cable.paint[i] = p; }
			else cb.cable.paint[cb.side.ordinal()] = p;
			cb.cable.markDirty();
		}
	}
}