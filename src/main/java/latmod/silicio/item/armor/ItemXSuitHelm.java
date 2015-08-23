package latmod.silicio.item.armor;

import latmod.latblocks.api.ILBGlasses;
import latmod.silicio.Silicio;
import latmod.silicio.item.ItemMaterialsSil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemXSuitHelm extends ItemXSuit implements ILBGlasses
{
	public ItemXSuitHelm(String s)
	{ super(s, 0); }
	
	public void loadRecipes()
	{
		Silicio.mod.recipes.addRecipe(new ItemStack(this), "PPP", "P P",
				'P', ItemMaterialsSil.XSUIT_PLATE);
	}
	
	public boolean areLBGlassesActive(ItemStack is, EntityPlayer ep)
	{ return true; }
}