package latmod.silicio.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvEntry
{
	public final IInventory inv;
	public final int side;
	public final int priority;
	public final ItemStack filter;
	public final boolean useDmg;
	public final boolean useNBT;
	
	public InvEntry(IInventory i, int s, int p, ItemStack f, boolean d, boolean n)
	{ inv = i; side = s; priority = p; filter = f; useDmg = d; useNBT = n; }
	
	public boolean isValid(ItemStack is)
	{
		if(is == null) return false;
		if(filter == null) return true;
		
		if(filter.getItem() != is.getItem()) return false;
		if(useDmg && filter.getItemDamage() != is.getItemDamage()) return false;
		if(useNBT && !ItemStack.areItemStackTagsEqual(filter, is)) return false;
		
		return true;
	}
}