package latmod.silicio.item;
import latmod.core.util.FastList;
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
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
	}
}