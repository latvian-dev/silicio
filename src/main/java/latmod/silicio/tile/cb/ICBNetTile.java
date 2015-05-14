package latmod.silicio.tile.cb;

import latmod.silicio.item.modules.events.*;

public interface ICBNetTile
{
	public void onControllerConnected(EventControllerConnected e);
	public void onControllerDisconnected(EventControllerDisconnected e);
	public void onUpdateCB();
	public boolean isSideEnabled(int side);
}