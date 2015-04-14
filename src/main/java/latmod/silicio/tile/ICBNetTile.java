package latmod.silicio.tile;

import latmod.core.tile.ITileInterface;

public interface ICBNetTile extends ITileInterface
{
	public void onNetworkChanged(TileCBController c);
	public void preUpdate();
	public boolean isDisabled(int side);
}