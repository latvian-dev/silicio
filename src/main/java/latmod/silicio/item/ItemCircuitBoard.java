package latmod.silicio.item;
import latmod.core.recipes.LMRecipes;
import latmod.core.util.FastList;
import latmod.silicio.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.*;

public class ItemCircuitBoard extends ItemSil
{
	public ItemCircuitBoard(String s)
	{
		super(s);
		setMaxStackSize(8);
	}
	
	public void loadRecipes()
	{
		SilRecipes.addSmelter(new ItemStack(this), SilItems.Modules.EMPTY, LMRecipes.size(SilMat.SILICON, 8), 1200);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
	}
}