package latmod.silicio.item;

import latmod.core.InvUtils;
import net.minecraft.item.ItemStack;

public interface IItemCard
{
	public boolean isItemValid(ItemStack is, ItemStack item);
	
	public static class Helper
	{
		public boolean isValid(ItemStack is, ItemStack item)
		{
			if(item == null) return false;
			if(is == null) return true;
			if(is.getItem() instanceof IItemCard)
				return ((IItemCard)is.getItem()).isItemValid(is, item);
			return InvUtils.itemsEquals(is, item, false, true);
		}
	}
}