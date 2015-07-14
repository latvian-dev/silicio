package latmod.silicio.tile.cb;

import latmod.ftbu.core.util.FastList;
import latmod.silicio.tile.cb.events.EventCB;
import net.minecraft.world.World;

public class CBNetwork
{
	public final TileCBController controller;
	public final FastList<ICBNetTile> tiles;
	
	public CBNetwork(TileCBController c)
	{
		controller = c;
		tiles = new FastList<ICBNetTile>();
	}
	
	public boolean hasController()
	{ return controller != null && !controller.isInvalid(); }
	
	public boolean hasConflict()
	{ return hasController() && controller.hasConflict; }
	
	public boolean hasWorkingController()
	{ return hasController() && !controller.hasConflict; }
	
	public void notifyNetwork()
	{
		
	}
	
	public void sendEvent(EventCB e)
	{
		for(int i = 0; i < tiles.size(); i++)
			tiles.get(i).onCBNetworkEvent(e);
	}
	
	// Static //
	
	public static CBNetwork create(World w, int x, int y, int z)
	{
		TileCBController crtl = null;
		return new CBNetwork(crtl);
	}
	
	public static void notifyNetwork(World w, int x, int y, int z)
	{ create(w, x, y, z).notifyNetwork(); }
}