package latmod.silicio.tile.cb;

import latmod.ftbu.tile.TileLM;
import latmod.silicio.tile.cb.events.EventCB;

public class TileBasicCBNetTile extends TileLM implements ICBNetTile
{
	public CBNetwork net = new CBNetwork();
	
	public CBNetwork getCBNetwork()
	{ return net.hasController() ? net.controller.getCBNetwork() : net; }
	
	public void setCBNetwork(CBNetwork n)
	{ net = n; }
	
	public void onCBNetworkEvent(EventCB e)
	{
	}
	
	public void onUpdateCB()
	{
	}
	
	public boolean isSideEnabled(int side)
	{ return true; }
}