package latmod.silicio.item.modules.io;

import latmod.core.InvUtils;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSItem;
import latmod.silicio.tile.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemInput extends ItemModuleIO implements IToggable
{
	public static final ModuleCSItem cs_filter = new ModuleCSItem(0, "Filter");
	
	public ItemModuleItemInput(String s)
	{
		super(s);
		setTextureName("item");
		
		moduleConfig.add(cs_filter);
		channelNames[0] = "Input";
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "F", "M",
				'M', SilItems.Modules.INPUT,
				'F', SilItems.Modules.i_item_storage);
	}
	
	public void onChannelToggled(CircuitBoard cb, int MID, CBChannel c)
	{
		if(isEnabled(c, cb, MID, 0))
		{
			ItemStack itemT = cs_filter.getItem(cb.items[MID]);
			
			TileEntity te = cb.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IInventory)
			{
				IInventory inv = (IInventory)te;
				
				int idx = InvUtils.getFirstFilledIndex(inv, itemT, cb.sideOpposite);
				
				if(idx != -1)
				{
					ItemStack is0 = inv.getStackInSlot(idx);
					
					if(cb.cable.controller().addItem(is0, false))
					{
						is0 = InvUtils.reduceItem(is0);
						inv.setInventorySlotContents(idx, is0);
						//inv.markDirty();
					}
				}
			}
		}
	}
}