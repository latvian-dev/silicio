package latmod.silicio.item.modules.events;

import latmod.silicio.tile.cb.TileCBController;

public class EventChannelToggledTile extends EventCB
{
	public final int channel;
	public final boolean on;
	
	public EventChannelToggledTile(TileCBController c, int ch, boolean b)
	{ super(c); channel = ch; on = b; }
}