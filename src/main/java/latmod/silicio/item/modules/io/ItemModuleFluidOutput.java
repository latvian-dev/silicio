package latmod.silicio.item.modules.io;

import latmod.core.LatCoreMC;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.item.modules.events.EventUpdateModule;
import latmod.silicio.tile.cb.TankEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.*;

public class ItemModuleFluidOutput extends ItemModuleIO
{
	public static final ModuleCSFluid cs_fluid = new ModuleCSFluid(0, "Fluid");
	public static final ModuleCSMode cs_mode = new ModuleCSMode(1, "Mode");
	
	public ItemModuleFluidOutput(String s)
	{
		super(s);
		setTextureName("fluid");
		cs_mode.setModes(0, "Slow", "Medium", "Fast", "Very Fast");
		
		for(int i = 0; i < cs_mode.modes.length; i++)
			cs_mode.setDesc(i, "Fluid: " + ItemModuleFluidInput.mBucketsPerSecond[i] + "mB for " + ItemModuleFluidInput.powerUse[i] + " RF");
		
		moduleConfig.add(cs_fluid);
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
				'F', SilItems.Modules.i_fluid_storage);
	}
	
	public void onUpdate(EventUpdateModule e)
	{
		if(e.isTick(20))
		{
			FluidStack fs = cs_fluid.getFluid(e.item());
			if(fs == null) return;
			fs.amount = 1;
			
			int mode = cs_mode.get(e.item());
			int left = ItemModuleFluidInput.mBucketsPerSecond[mode];
			//int pw = ItemModuleFluidInput.powerUse[mode];
			
			TileEntity te = e.board.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
			{
				IFluidHandler fh = (IFluidHandler)te;
				if(!fh.canFill(e.board.sideOppositeF, fs.getFluid())) return;
				
				FluidTankInfo[] info = fh.getTankInfo(e.board.sideOppositeF);
				FluidStack fs0 = (info == null || info.length <= 0) ? null : info[0].fluid;
				
				for(TankEntry entry : e.controller.tankNetwork)
				{
					if(entry.filter == null || fs0 == null || entry.filter.isFluidEqual(fs0))
					{
						FluidStack drain = entry.tank.drain(entry.sideF, left, false);
						
						if(drain != null && drain.amount > 0)
						{
							int fill = fh.fill(e.board.sideF, drain, false);
							
							if(fill > 0)
							{
								drain = entry.tank.drain(entry.sideF, fill, true);
								left -= fh.fill(e.board.sideF, drain, true);
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