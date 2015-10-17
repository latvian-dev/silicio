package latmod.silicio.item.modules.io;

import latmod.ftbu.api.paint.Paint;
import latmod.ftbu.inv.*;
import latmod.ftbu.util.EnumDyeColor;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.cb.events.EventChannelToggled;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;

public class ItemModulePainter extends ItemModuleIO implements IToggable
{
	public static final ModuleCSBool cs_paint_all = new ModuleCSBool(0, "Paint all block");
	
	public static final ModuleCSItem cs_paint_on = new ModuleCSItem(1, "Paint [ON]")
	{
		public boolean isValid(ItemStack is)
		{ return is != null && is.getItem() instanceof ItemBlock && !Block.getBlockFromItem(is.getItem()).hasTileEntity(is.getItemDamage()); }
	};
	
	public static final ModuleCSItem cs_paint_off = new ModuleCSItem(2, "Paint [OFF]")
	{
		public boolean isValid(ItemStack is)
		{ return is == null || cs_paint_on.isValid(is); }
	};
	
	public ItemModulePainter(String s)
	{
		super(s);
		
		cs_paint_on.defaultItem = new ItemStack(Blocks.planks, 1, 0);
		cs_paint_off.defaultItem = null;
		
		moduleConfig.add(cs_paint_all);
		moduleConfig.add(cs_paint_on);
		moduleConfig.add(cs_paint_off);
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		ItemStack is = ItemStackTypeAdapter.parseItem("LatBlocks:painterParts 1 3");
		
		if(is != null)
			mod.recipes.addShapelessRecipe(new ItemStack(this), SilItems.Modules.OUTPUT, is);
		else
			mod.recipes.addRecipe(new ItemStack(this), "1D2", "DMD", "3D4",
					'M', SilItems.Modules.OUTPUT,
					'D', ODItems.DIAMOND,
					'1', EnumDyeColor.BLUE.dyeName,
					'2', EnumDyeColor.GREEN.dyeName,
					'3', EnumDyeColor.YELLOW.dyeName,
					'4', EnumDyeColor.RED.dyeName);
	}
	
	public void onChannelToggled(EventChannelToggled e)
	{
		if(!e.cable.hasCover) return;
		int ch = e.getChannel(0);
		
		if(ch != -1 && e.channel == ch)
		{
			Paint p = null;
			
			ItemStack isp = (e.isEnabled0(ch, -1, false) ? cs_paint_on.getItem(e.item()) : cs_paint_off.getItem(e.item()));
			
			if(isp == null) return;
			
			if(isp != null && isp.getItem() instanceof ItemBlock)
				p = new Paint(Block.getBlockFromItem(isp.getItem()), isp.getItemDamage());
			
			if(cs_paint_all.get(e.item()))
			{ for(int i = 0; i < 6; i++) e.cable.paint[i] = p; }
			else e.cable.paint[e.board.side] = p;
			e.cable.markDirty();
		}
	}
}