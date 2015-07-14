package latmod.silicio.tile.cb;

import latmod.silicio.tile.cb.events.EventProvideSignalsTile;

public interface ISignalProviderTile extends ICBNetTile
{
	public void provideSignalsTile(EventProvideSignalsTile e);
}