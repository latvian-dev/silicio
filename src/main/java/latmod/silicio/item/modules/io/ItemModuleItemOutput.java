package latmod.silicio.item.modules.io;

import latmod.core.InvUtils;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSItem;
import latmod.silicio.tile.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemOutput extends ItemModuleIO
{
	public static final ModuleCSItem cs_item = new ModuleCSItem(0, "Item");
	
	public final int itemsPerSecond;
	
	public ItemModuleItemOutput(String s, int i)
	{
		super(s);
		itemsPerSecond = i;
		setTextureName("item");
		
		moduleConfig.add(cs_item);
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.OUTPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "F", "M",
				'M', SilItems.Modules.OUTPUT,
				'F', SilItems.Modules.i_item_storage);
	}
	
	public void onUpdate(CircuitBoard cb, int MID)
	{
		if(cb.tick % 20L == 0L)
		{
			ItemStack itemT = cs_item.getItem(cb.items[MID]);
			
			if(itemT == null) return;
			
			TileEntity te = cb.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IInventory)
			{
				IInventory inv = (IInventory)te;
				
				for(int i = 0; i < itemsPerSecond; i++)
				{
					int idx = InvUtils.getFirstIndexWhereFits(inv, itemT, cb.sideOpposite);
					
					if(idx != -1 && cb.cable.controller().requestItem(itemT, false))
					{
						ItemStack is0 = inv.getStackInSlot(idx);
						if(is0 == null) is0 = InvUtils.singleCopy(itemT);
						else is0.stackSize++;
						inv.setInventorySlotContents(idx, is0);
						//inv.markDirty();
					}
					else return;
				}
			}
		}
	}
}