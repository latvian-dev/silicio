package latmod.silicio.item.armor;

import latmod.latblocks.api.ILBGlasses;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemXSuitHelm extends ItemXSuit implements ILBGlasses
{
	public ItemXSuitHelm(String s)
	{ super(s, 0); }
	
	public void loadRecipes()
	{
	}
	
	public boolean areLBGlassesActive(ItemStack is, EntityPlayer ep)
	{ return true; }
}