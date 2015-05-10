package latmod.silicio.item.modules.io;

import latmod.core.InvUtils;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemOutput extends ItemModuleIO
{
	public static final ModuleCSItem cs_item = new ModuleCSItem(0, "Item");
	public static final ModuleCSMode cs_mode = new ModuleCSMode(1, "Mode");
	
	public ItemModuleItemOutput(String s)
	{
		super(s);
		setTextureName("item");
		
		cs_mode.setModes(0, "Slow", "Medium", "Fast", "Very Fast");
		
		for(int i = 0; i < cs_mode.modes.length; i++)
			cs_mode.setDesc(i, "Items: " + ItemModuleItemInput.itemsPerSecond[i] + "x for " + ItemModuleItemInput.powerUse[i] + " RF");
		
		moduleConfig.add(cs_item);
		moduleConfig.add(cs_mode);
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
		if(cb.tick % 20L == MID)
		{
			ItemStack itemT = cs_item.getItem(cb.items[MID]);
			
			if(itemT == null) return;
			
			TileEntity te = cb.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IInventory)
			{
				IInventory inv = (IInventory)te;
				
				int mode = cs_mode.get(cb.items[MID]);
				int ips = ItemModuleItemInput.itemsPerSecond[mode];
				int pw = ItemModuleItemInput.powerUse[mode];
				
				for(int i = 0; i < ips; i++)
				{
					int idx = InvUtils.getFirstIndexWhereFits(inv, itemT, cb.sideOpposite);
					
					if(idx != -1 && (pw == 0 || cb.cable.controller().hasEnergy(pw)) && cb.cable.controller().requestItem(itemT, false))
					{
						if(pw > 0) cb.cable.controller().extractEnergy(pw);
						
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