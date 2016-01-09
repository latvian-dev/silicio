package latmod.silicio.item.armor;

import latmod.silicio.Silicio;
import latmod.silicio.item.ItemMaterialsSil;
import net.minecraft.item.ItemStack;

public class ItemXSuitBody extends ItemXSuit
{
	public ItemXSuitBody(String s)
	{ super(s, 1); }
	
	public void loadRecipes()
	{
		Silicio.mod.recipes.addRecipe(new ItemStack(this), "P P", "PPP", "PPP", 'P', ItemMaterialsSil.XSUIT_PLATE);
	}
}