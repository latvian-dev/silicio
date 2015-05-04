package latmod.silicio.item.modules.io;

import latmod.core.LatCoreMC;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.*;

public class ItemModuleFluidOutput extends ItemModuleIO implements IToggable
{
	public static final ModuleCSFluid cs_fluid = new ModuleCSFluid(0, "Fluid");
	public static final ModuleCSNum cs_amount = new ModuleCSNum(1, "Amount");
	
	public ItemModuleFluidOutput(String s)
	{
		super(s);
		setTextureName("fluid");
		cs_amount.setValues(1000, 1, 1000, 10);
		moduleConfig.add(cs_fluid);
		moduleConfig.add(cs_amount);
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
				'F', SilItems.Modules.i_fluid_storage);
	}
	
	public void onChannelToggled(CircuitBoard cb, int MID, CBChannel c)
	{
		if(isEnabled(c, cb, MID, 0))
		{
			FluidStack fs = cs_fluid.getFluid(cb.items[MID]);
			if(fs == null) return;
			fs.amount = 1;
			int left = cs_amount.get(cb.items[MID]);
			
			TileEntity te = cb.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
			{
				IFluidHandler fh = (IFluidHandler)te;
				if(!fh.canFill(cb.sideOppositeF, fs.getFluid())) return;
				
				FluidTankInfo[] info = fh.getTankInfo(cb.sideOppositeF);
				FluidStack fs0 = (info == null || info.length <= 0) ? null : info[0].fluid;
				
				for(TankEntry e : cb.cable.controller().tankNetwork.sortToNew(null))
				{
					if(e.filter == null || fs0 == null || e.filter.isFluidEqual(fs0))
					{
						FluidStack drain = e.tank.drain(e.sideF, left, false);
						
						if(drain != null && drain.amount > 0)
						{
							int fill = fh.fill(cb.sideF, drain, false);
							
							if(fill > 0)
							{
								drain = e.tank.drain(e.sideF, fill, true);
								left -= fh.fill(cb.sideF, drain, true);
								if(left < 0) LatCoreMC.printChat(null, "Error!");
								if(left <= 0) return;
							}
						}
					}
				}
			}
		}
	}
}