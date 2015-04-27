package latmod.silicio.item.modules.io;

import latmod.core.InvUtils;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSItem;
import latmod.silicio.tile.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemOutput extends ItemModuleIO implements IToggable
{
	public static final ModuleCSItem cs_item = new ModuleCSItem(0, "Item");
	
	public ItemModuleItemOutput(String s)
	{
		super(s);
		setTextureName("item");
		
		moduleConfig.add(cs_item);
		channelNames[0] = "Input";
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.OUTPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "F", "M",
				'M', SilItems.Modules.OUTPUT,
				'F', SilItems.Modules.i_item_storage);
	}
	
	public void onChannelToggled(CircuitBoard cb, int MID, CBChannel c)
	{
		if(isEnabled(c, cb, MID, 0))
		{
			ItemStack itemT = cs_item.getItem(cb.items[MID]);
			
			if(itemT == null) return;
			
			TileEntity te = cb.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IInventory)
			{
				IInventory inv = (IInventory)te;
				
				int idx = InvUtils.getFirstIndexWhereFits(inv, itemT, cb.sideOpposite);
				
				if(idx != -1 && cb.cable.controller().requestItem(itemT, false))
				{
					ItemStack is0 = inv.getStackInSlot(idx);
					if(is0 == null) is0 = InvUtils.singleCopy(itemT);
					else is0.stackSize++;
					inv.setInventorySlotContents(idx, is0);
					//inv.markDirty();
				}
			}
		}
	}
}