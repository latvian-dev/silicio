package latmod.silicio.tile.cb;

public interface ICBNetTile
{
	public void preUpdate(TileCBController c);
	public void onUpdateCB();
	public void onControllerDisconnected();
	public boolean isSideEnabled(int side);
}