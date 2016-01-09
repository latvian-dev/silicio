package latmod.silicio.tile.cb.events;

import latmod.silicio.tile.cb.CBNetwork;

public class EventChannelToggledTile extends EventCB
{
	public final int channel;
	public final boolean on;
	
	public EventChannelToggledTile(CBNetwork c, int ch, boolean b)
	{
		super(c);
		channel = ch;
		on = b;
	}
}