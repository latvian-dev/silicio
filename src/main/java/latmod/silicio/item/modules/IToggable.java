package latmod.silicio.item.modules;

import latmod.silicio.item.modules.events.EventChannelToggled;

public interface IToggable
{
	public void onChannelToggled(EventChannelToggled e);
}