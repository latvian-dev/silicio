package latmod.silicio.item.modules.io;

import latmod.ftbu.core.inv.LMInvUtils;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.cb.events.EventUpdateModule;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemInput extends ItemModuleIO
{
	public static final ModuleCSItem cs_filter = new ModuleCSItem(0, "Filter");
	public static final ModuleCSMode cs_mode = new ModuleCSMode(1, "Mode");
	
	public static final int[] itemsPerSecond = { 1, 4, 16, 64 };
	public static final int[] powerUse = { 0, 10, 200, 1200 };
	
	public ItemModuleItemInput(String s)
	{
		super(s);
		setTextureName("item");
		cs_mode.setModes(0, "Slow", "Medium", "Fast", "Very Fast");
		
		for(int i = 0; i < cs_mode.modes.length; i++)
			cs_mode.setDesc(i, "Items: " + itemsPerSecond[i] + "x for " + powerUse[i] + " RF");
		
		moduleConfig.add(cs_filter);
		moduleConfig.add(cs_mode);
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "F", "M",
				'M', SilItems.Modules.INPUT,
				'F', SilItems.Modules.i_item_storage);
	}
	
	public void onUpdate(EventUpdateModule e)
	{
		if(e.isTick(20))
		{
			ItemStack itemT = cs_filter.getItem(e.item());
			
			TileEntity te = e.board.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IInventory)
			{
				IInventory inv = (IInventory)te;
				
				int mode = cs_mode.get(e.item());
				int ips = itemsPerSecond[mode];
				int pw = powerUse[mode];
				
				for(int i = 0; i < ips; i++)
				{
					int idx = LMInvUtils.getFirstFilledIndex(inv, itemT, e.board.sideOpposite);
					
					if(idx != -1)
					{
						ItemStack is0 = inv.getStackInSlot(idx);
						
						if((pw == 0 || e.net.controller.hasEnergy(pw)) && e.net.controller.addItem(is0, false))
						{
							if(pw > 0) e.net.controller.extractEnergy(pw);
							is0 = LMInvUtils.reduceItem(is0);
							inv.setInventorySlotContents(idx, is0);
							//inv.markDirty();
						}
						else return;
					}
					else return;
				}
			}
		}
	}
}