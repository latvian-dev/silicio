package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ItemModuleFluidInput extends ItemModuleIO
{
	public static final ModuleCSFluid cs_fluid = new ModuleCSFluid(0, "Fluid");
	public static final ModuleCSNum cs_amount = new ModuleCSNum(1, "Amount");
	public static final ModuleCSMode cs_mode = new ModuleCSMode(2, "Mode");
	
	public static final int[] mBucketsPerSecond = { 200, 1000, 4000, 16000 };
	public static final int[] powerUse = { 0, 10, 200, 1200 };
	
	public ItemModuleFluidInput(String s)
	{
		super(s);
		setTextureName("fluid");
		
		cs_amount.setValues(1000, 1, 1000, 10);
		cs_mode.setModes(0, "Slow", "Medium", "Fast", "Very Fast");
		
		moduleConfig.add(cs_fluid);
		moduleConfig.add(cs_amount);
		moduleConfig.add(cs_mode);
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void onUpdate(CircuitBoard cb, int MID)
	{
		if(cb.tick % 20L == MID)
		{
			FluidStack fs = cs_fluid.getFluid(cb.items[MID]);
			if(fs != null) fs.amount = cs_amount.get(cb.items[MID]);
		}
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "F", "M",
				'M', SilItems.Modules.INPUT,
				'F', SilItems.Modules.i_fluid_storage);
	}
}