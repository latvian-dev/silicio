package latmod.silicio.item.modules.io;

import latmod.core.ODItems;
import latmod.core.util.FastList;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemModuleItemStorage extends ItemModuleIO
{
	public static final ModuleCSNum cs_priority = new ModuleCSNum(0, "Priority");
	public static final ModuleCSItem cs_filter = new ModuleCSItem(1, "Filter");
	
	public ItemModuleItemStorage(String s)
	{
		super(s);
		setTextureName("item");
		
		moduleConfig.add(cs_priority);
		moduleConfig.add(cs_filter);
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
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
		TileEntity te = cb.getFacingTile();
		if(te != null && !te.isInvalid() && te instanceof IInventory)
			list.add(new InvEntry((IInventory)te, cb.sideOpposite, cs_priority.get(cb.items[MID]), cs_filter.getItem(cb.items[MID])));
	}
}