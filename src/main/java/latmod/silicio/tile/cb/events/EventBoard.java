package latmod.silicio.tile.cb.events;

import latmod.ftbu.core.util.LatCore;
import latmod.silicio.tile.cb.*;

public class EventBoard extends EventCB
{
	public final TileCBCable cable;
	public final CircuitBoard board;
	
	public EventBoard(CBNetwork c, CircuitBoard cb)
	{ super(c); board = cb; cable = board.cable; }
	
	public int hashCode()
	{ return LatCore.hashCode(super.hashCode(), cable.xCoord, cable.yCoord, cable.zCoord, board.side); }
}