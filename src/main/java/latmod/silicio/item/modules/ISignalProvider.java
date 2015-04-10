package latmod.silicio.item.modules;

import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;

public interface ISignalProvider extends ICBModule
{
	public void provideSignals(ItemStack is, CircuitBoard t);
}