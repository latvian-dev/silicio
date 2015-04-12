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
	public static final ModuleCSItem cs_paint_1 = new ModuleCSItem(0, "Paint [High]")
	{
		public boolean isValid(ItemStack is)
		{ return is == null || (is.getItem() instanceof ItemBlock && !Block.getBlockFromItem(is.getItem()).hasTileEntity(is.getItemDamage())); }
	};
	
	public static final ModuleCSItem cs_paint_0 = new ModuleCSItem(1, "Paint [Low]")
	{
		public boolean isValid(ItemStack is)
		{ return is == null || (is.getItem() instanceof ItemBlock && !Block.getBlockFromItem(is.getItem()).hasTileEntity(is.getItemDamage())); }
	};
	
	public static final ModuleCSBool cs_paint_all = new ModuleCSBool(2, "Paint all block");
	
	public ItemModulePainter(String s)
	{
		super(s);
		
		cs_paint_1.defaultItem = new ItemStack(Blocks.wool, 1, 5);
		moduleConfig.add(cs_paint_1);
		
		cs_paint_0.defaultItem = new ItemStack(Blocks.wool, 1, 14);
		moduleConfig.add(cs_paint_0);
		
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
	
	public void onChannelToggled(ItemStack is, CircuitBoard t, CBChannel c)
	{
		if(t.cable.hasCover && c == getChannel(is, t, 0))
		{
			Paint p = null;
			
			ItemStack isp = c.isEnabled() ? cs_paint_1.get(is) : cs_paint_0.get(is);
			
			if(isp != null && isp.getItem() instanceof ItemBlock)
				p = new Paint(Block.getBlockFromItem(isp.getItem()), isp.getItemDamage());
			
			t.cable.paint[t.side.ordinal()] = p;
			t.cable.markDirty();
		}
	}
}