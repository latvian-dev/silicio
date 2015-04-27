package latmod.silicio.item.modules.io;

import latmod.core.ODItems;
import latmod.core.util.FastList;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemModuleFluidStorage extends ItemModuleIO
{
	public static final ModuleCSNum cs_priority = new ModuleCSNum(0, "Priority");
	public static final ModuleCSFluid cs_filter = new ModuleCSFluid(1, "Filter");
	
	public ItemModuleFluidStorage(String s)
	{
		super(s);
		setTextureName("fluid");
		
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
				'W', Items.bucket);
	}
	
	public void updateTankNet(CircuitBoard cb, int MID, FastList<TankEntry> list)
	{
		TileEntity te = cb.getFacingTile();
		
		if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
		{ list.add(new TankEntry((IFluidHandler)te, cb.sideOpposite, cs_priority.get(cb.items[MID]), cs_filter.getFluid(cb.items[MID]))); }
	}
}