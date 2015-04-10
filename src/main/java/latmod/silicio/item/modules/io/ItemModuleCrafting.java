package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleCrafting extends ItemModuleIO
{
	public ItemModuleCrafting(String s)
	{ super(s); }
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " C ", "IMO",
				'C', Blocks.crafting_table,
				'M', SilItems.Modules.EMPTY,
				'I', SilItems.Modules.i_item_in,
				'O', SilItems.Modules.i_item_out);
	}
}