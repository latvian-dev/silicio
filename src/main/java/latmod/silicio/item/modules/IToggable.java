package latmod.silicio.item.modules;

import latmod.silicio.tile.cb.events.EventChannelToggled;

public interface IToggable
{
	public void onChannelToggled(EventChannelToggled e);
}