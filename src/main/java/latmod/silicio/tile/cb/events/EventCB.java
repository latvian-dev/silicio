package latmod.silicio.tile.cb.events;

import latmod.silicio.tile.cb.CBNetwork;

public class EventCB
{
	public final CBNetwork net;
	
	public EventCB(CBNetwork c)
	{ net = c; }
	
	public int hashCode()
	{ return net.controller.hashCode(); }
	
	public int getUID(int max)
	{ return (hashCode() % max) & (max - 1); }
	
	public boolean isTick(int i)
	{ return net.controller.tick % i == getUID(i); }
	
	public boolean isEnabled0(int ch0, int ch, boolean def)
	{ return net.controller.isEnabled(ch0, ch, def); }
	
	public boolean setEnabled0(int ch)
	{ return net.controller.setEnabled(ch); }
	
	public boolean is(Class<? extends EventCB> c)
	{ return getClass() == c; }
}