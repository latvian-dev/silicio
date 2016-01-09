package latmod.silicio.item.armor;

import latmod.silicio.Silicio;
import latmod.silicio.item.ItemMaterialsSil;
import net.minecraft.item.ItemStack;

public class ItemXSuitBoots extends ItemXSuit
{
	public ItemXSuitBoots(String s)
	{ super(s, 3); }
	
	public void loadRecipes()
	{
		Silicio.mod.recipes.addRecipe(new ItemStack(this), "P P", "P P", 'P', ItemMaterialsSil.XSUIT_PLATE);
	}
}