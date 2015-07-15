package latmod.silicio.item;

import latmod.ftbu.core.inv.InvUtils;
import net.minecraft.item.ItemStack;

public interface IItemCard
{
	public boolean isItemValid(ItemStack is, ItemStack item);
	
	public static class Helper
	{
		public static boolean isValid(ItemStack filter, ItemStack item)
		{
			if(item == null) return false;
			if(filter == null) return true;
			if(filter.getItem() instanceof IItemCard)
				return ((IItemCard)filter.getItem()).isItemValid(filter, item);
			return InvUtils.itemsEquals(filter, item, false, true);
		}
	}
}