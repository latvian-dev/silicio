package latmod.silicio.item.modules.events;

import latmod.silicio.tile.cb.ModuleEntry;

public class EventChannelToggled extends EventModule
{
	public final int channel;
	public final boolean on;
	
	public EventChannelToggled(ModuleEntry e, int ch, boolean b)
	{
		super(e);
		channel = ch;
		on = b;
	}
}