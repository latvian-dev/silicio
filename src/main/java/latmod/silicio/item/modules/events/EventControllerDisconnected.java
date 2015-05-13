package latmod.silicio.item.modules.events;

import latmod.silicio.tile.cb.TileCBController;

public class EventControllerDisconnected extends EventCB
{
	public final boolean hasConflict;
	
	public EventControllerDisconnected(TileCBController c, boolean b)
	{
		super(c);
		hasConflict = b;
	}
}