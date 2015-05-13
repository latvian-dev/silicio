package latmod.silicio.tile.cb;

import latmod.silicio.item.modules.events.EventProvideSignalsTile;

public interface ISignalProviderTile extends ICBNetTile
{
	public void provideSignalsTile(EventProvideSignalsTile e);
}