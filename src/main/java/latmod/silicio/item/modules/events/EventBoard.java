package latmod.silicio.item.modules.events;

import latmod.core.util.LatCore;
import latmod.silicio.tile.cb.*;

public class EventBoard extends EventCB
{
	public final TileCBCable cable;
	public final CircuitBoard board;
	
	public EventBoard(TileCBController c, CircuitBoard cb)
	{
		super(c);
		board = cb;
		cable = board.cable;
	}
	
	public int hashCode()
	{ return LatCore.hashCode(super.hashCode(), cable.xCoord, cable.yCoord, cable.zCoord, board.side); }
}