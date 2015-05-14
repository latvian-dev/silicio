package latmod.silicio.tile.cb;

import latmod.silicio.item.modules.events.EventChannelToggledTile;

public interface IToggableTile extends ICBNetTile
{
	public void onChannelToggledTile(EventChannelToggledTile e);
}