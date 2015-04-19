package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.config.ModuleCSItem;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;

public class ItemModuleItemOutput extends ItemModuleIO
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
	
	public void onUpdate(CircuitBoard cb, int MID)
	{
		/*
		if(cb.tick % 4 == 0 && cb.cable.isServer())
		{
			CBChannel c = getChannel(cb, MID, 0);
			if(c != CBChannel.NONE && !c.isEnabled()) return;
			
			TileEntity te = cb.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IInventory)
			{
				IInventory inv = (IInventory)te;
				ForgeDirection side = cb.side.getOpposite();
				int slots[] = InvUtils.getAllSlots(inv, side.ordinal());
				
				for(int i = 0; i < slots.length; i++)
				{
					ItemStack is1 = inv.getStackInSlot(slots[i]);
					
					if(is1 != null)
					{
						if(inv instanceof ISidedInventory && !((ISidedInventory)inv).canExtractItem(slots[i], is1, side.ordinal()))
							continue;
						
						InvEntry ie = cb.cable.controller.getInventoryFor(is1);
						
						if(ie != null && InvUtils.addSingleItemToInv(is1, ie.inv, ie.side, true))
						{
							is1 = InvUtils.reduceItem(is1);
							inv.setInventorySlotContents(slots[i], is1);
							
							if(is1 == null)
							{
								inv.markDirty();
								ie.inv.markDirty();
							}
							
							return;
						}
					}
				}
			}
		}
		*/
	}
}