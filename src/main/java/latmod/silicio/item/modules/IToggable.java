package latmod.silicio.item.modules;

import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;

public interface IToggable extends ICBModule
{
	public void onChannelToggled(ItemStack is, CircuitBoard t, CBChannel c);
}