package latmod.silicio.item.modules;

import latmod.silicio.tile.*;

public interface IToggable extends ICBModule
{
	public void onChannelToggled(CircuitBoard cb, int MID, CBChannel c);
}