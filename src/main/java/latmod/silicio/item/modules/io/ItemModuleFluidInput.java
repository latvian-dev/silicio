package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ItemModuleFluidInput extends ItemModuleIO implements IToggable
{
	public static final ModuleCSFluid cs_fluid = new ModuleCSFluid(0, "Fluid");
	public static final ModuleCSNum cs_amount = new ModuleCSNum(1, "Amount");
	
	public ItemModuleFluidInput(String s)
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
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "F", "M",
				'M', SilItems.Modules.INPUT,
				'F', SilItems.Modules.i_fluid_storage);
	}
	
	public void onChannelToggled(CircuitBoard cb, int MID, CBChannel c)
	{
		if(isEnabled(c, cb, MID, 0))
		{
			FluidStack fs = cs_fluid.getFluid(cb.items[MID]);
			if(fs != null) fs.amount = cs_amount.get(cb.items[MID]);
		}
	}
}