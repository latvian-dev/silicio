package latmod.silicio.item.modules.events;

import latmod.silicio.tile.cb.TileCBController;

public class EventCB
{
	public final TileCBController controller;
	
	public EventCB(TileCBController c)
	{ controller = c; }
	
	public int hashCode()
	{ return controller.hashCode(); }
	
	public int getUID(int max)
	{ return (hashCode() % max) & (max - 1); }
	
	public boolean isTick(int i)
	{ return controller.tick % i == getUID(i); }
	
	public boolean isEnabled0(int ch0, int ch, boolean def)
	{ return controller.isEnabled(ch0, ch, def); }
	
	public boolean setEnabled0(int ch)
	{ return controller.setEnabled(ch); }
}