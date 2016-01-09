package latmod.silicio.item.modules.io;

import ftb.lib.item.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.cb.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

public class ItemModuleFluidStorage extends ItemModuleIO implements ITankProvider
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
		getMod().recipes.addRecipe(new ItemStack(this), "RWT", "PMP", 'R', ODItems.REDSTONE, 'P', ODItems.IRON, 'M', SilItems.Modules.EMPTY, 'T', SilItems.b_cbcable, 'W', Items.bucket);
	}
	
	public void updateTankNet(ModuleEntry e, List<TankEntry> l)
	{
		TileEntity te = e.board.getFacingTile();
		if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
			l.add(new TankEntry((IFluidHandler) te, e.board.sideOpposite, cs_priority.get(e.stack), cs_filter.getFluid(e.stack)));
	}
}