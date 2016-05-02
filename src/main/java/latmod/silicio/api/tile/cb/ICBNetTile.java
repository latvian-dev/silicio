package latmod.silicio.api.tile.cb;

import ftb.lib.api.tile.ITileEntity;

import java.util.UUID;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ICBNetTile extends ITileEntity
{
	UUID getUUID();
	void setUUID(UUID id);
	boolean isDeviceOnline(ICBController c);
}