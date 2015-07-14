package latmod.silicio.tile.cb;

import latmod.silicio.tile.cb.events.EventCB;

public interface ICBNetTile
{
	public CBNetwork getCBNetwork();
	public void setCBNetwork(CBNetwork n);
	public void onCBNetworkEvent(EventCB e);
	public void onUpdateCB();
	public boolean isSideEnabled(int side);
}