package latmod.silicio.tile;

import latmod.core.tile.ITileInterface;

public interface ICBNetTile extends ITileInterface
{
	public void preUpdate(TileCBController c);
	public void onUpdateCB();
	public void onControllerDisconnected();
	public boolean isSideEnabled(int side);
}