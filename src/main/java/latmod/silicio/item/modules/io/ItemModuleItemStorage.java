package latmod.silicio.item.modules.io;

import latmod.core.ODItems;
import latmod.core.util.FastList;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemStorage extends ItemModuleIO
{
	public ItemModuleItemStorage(String s)
	{
		super(s);
		setTextureName("item");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "RWT", "PMP",
				'R', ODItems.REDSTONE,
				'P', ODItems.IRON,
				'M', SilItems.Modules.EMPTY,
				'T', SilItems.b_cbcable,
				'W', Blocks.chest);
	}
	
	public void updateInvNet(CircuitBoard cb, int MID, FastList<InvEntry> list)
	{
		CBChannel c = getChannel(cb, MID, 0);
		if(c != CBChannel.NONE && !c.isEnabled()) return;
		
		TileEntity te = cb.getFacingTile();
		
		if(te != null && !te.isInvalid() && te instanceof IInventory)
		{
			InvEntry e = new InvEntry();
			e.inv = (IInventory)te;
			e.side = cb.sideOpposite.ordinal();
			e.priority = 1;
			list.add(e);
		}
	}
}