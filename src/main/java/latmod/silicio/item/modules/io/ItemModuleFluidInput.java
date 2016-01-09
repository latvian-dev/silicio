package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.cb.events.EventUpdateModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ItemModuleFluidInput extends ItemModuleIO
{
	public static final ModuleCSFluid cs_fluid = new ModuleCSFluid(0, "Fluid");
	public static final ModuleCSMode cs_mode = new ModuleCSMode(1, "Mode");
	
	public static final int[] mBucketsPerSecond = {200, 1000, 4000, 16000};
	public static final int[] powerUse = {0, 10, 200, 1200};
	
	public ItemModuleFluidInput(String s)
	{
		super(s);
		setTextureName("fluid");
		cs_mode.setModes(0, "Slow", "Medium", "Fast", "Very Fast");
		
		for(int i = 0; i < cs_mode.modes.length; i++)
			cs_mode.setDesc(i, "Fluid: " + mBucketsPerSecond[i] + "mB for " + powerUse[i] + " RF");
		
		moduleConfig.add(cs_fluid);
		moduleConfig.add(cs_mode);
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
	public void onUpdate(EventUpdateModule e)
	{
		if(e.isTick(20))
		{
			int mode = cs_mode.get(e.item());
			
			FluidStack fs = cs_fluid.getFluid(e.item());
			if(fs != null) fs.amount = mBucketsPerSecond[mode];
		}
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "F", "M", 'M', SilItems.Modules.INPUT, 'F', SilItems.Modules.i_fluid_storage);
	}
}