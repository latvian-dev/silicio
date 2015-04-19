package latmod.silicio.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvEntry
{
	public final IInventory inv;
	public final int side;
	public final int priority;
	public final ItemStack filter;
	
	public InvEntry(IInventory i, int s, int p, ItemStack is)
	{ inv = i; side = s; priority = p; filter = is; }
}