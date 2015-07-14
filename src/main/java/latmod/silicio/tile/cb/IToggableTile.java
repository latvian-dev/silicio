package latmod.silicio.tile.cb;

import latmod.silicio.tile.cb.events.EventChannelToggledTile;

public interface IToggableTile extends ICBNetTile
{
	public void onChannelToggledTile(EventChannelToggledTile e);
}