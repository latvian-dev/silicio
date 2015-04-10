package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import net.minecraft.item.ItemStack;

public class ItemModuleFluidOutput extends ItemModuleIO
{
	public ItemModuleFluidOutput(String s)
	{
		super(s);
		setTextureName("fluid");
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
}