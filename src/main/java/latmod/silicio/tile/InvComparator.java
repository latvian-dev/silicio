package latmod.silicio.tile;

import java.util.Comparator;

import latmod.core.InvUtils;
import net.minecraft.item.ItemStack;

public class InvComparator implements Comparator<InvEntry>
{
	public ItemStack filter;
	
	public InvComparator(ItemStack is)
	{
		filter = is;
	}
	
	public int compare(InvEntry o1, InvEntry o2)
	{
		int i = Integer.compare(o1.priority, o2.priority);
		if(i == 0)
		{
			int c1 = getItemCount(o1);
			int c2 = getItemCount(o2);
			i = Integer.compare(c1, c2);
		}
		
		return -i;
	}
	
	private int getItemCount(InvEntry e)
	{
		int c = 0;
		
		int[] slots = InvUtils.getAllSlots(e.inv, e.side);
		
		for(int i = 0; i < slots.length; i++)
		{
			ItemStack is = e.inv.getStackInSlot(slots[i]);
			if(is != null && InvUtils.itemsEquals(is, filter, false, true))
				c += is.stackSize;
		}
		
		return c;
	}
}