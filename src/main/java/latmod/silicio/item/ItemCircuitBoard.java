package latmod.silicio.item;
import cpw.mods.fml.relauncher.*;
import latmod.lib.FastList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCircuitBoard extends ItemSil
{
	public ItemCircuitBoard(String s)
	{
		super(s);
		setMaxStackSize(8);
	}
	
	public void loadRecipes()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
	}
}