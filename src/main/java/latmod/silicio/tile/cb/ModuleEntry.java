package latmod.silicio.tile.cb;

import latmod.silicio.item.modules.ItemModule;
import net.minecraft.item.ItemStack;

public class ModuleEntry
{
	public final CBNetwork net;
	public final CircuitBoard board;
	public final int moduleID;
	public final ItemStack stack;
	public final ItemModule item;
	
	public ModuleEntry(CBNetwork c, CircuitBoard cb, int mid)
	{
		net = c;
		board = cb;
		moduleID = mid;
		stack = cb.items[moduleID];
		item = (ItemModule) stack.getItem();
	}
}