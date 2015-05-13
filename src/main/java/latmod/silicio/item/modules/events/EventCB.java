package latmod.silicio.item.modules.events;

import latmod.silicio.tile.cb.TileCBController;

public class EventCB
{
	public final TileCBController controller;
	public final boolean isServer;
	
	public EventCB(TileCBController c)
	{
		controller = c;
		isServer = c.isServer();
	}
	
	public int hashCode()
	{ return controller.hashCode(); }
	
	public int getUID(int max)
	{ return (hashCode() % max) & (max - 1); }
	
	public boolean isTick(int i)
	{ return controller.tick % i == getUID(i); }
}