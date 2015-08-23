package latmod.silicio.item.armor;

import latmod.silicio.Silicio;
import latmod.silicio.item.ItemMaterialsSil;
import net.minecraft.item.ItemStack;

public class ItemXSuitLegs extends ItemXSuit
{
	public ItemXSuitLegs(String s)
	{ super(s, 2); }
	
	public void loadRecipes()
	{
		Silicio.mod.recipes.addRecipe(new ItemStack(this), "PPP", "P P", "P P",
				'P', ItemMaterialsSil.XSUIT_PLATE);
	}
}