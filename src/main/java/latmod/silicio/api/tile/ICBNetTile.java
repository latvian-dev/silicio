package latmod.silicio.api.tile;

import ftb.lib.BlockDimPos;
import ftb.lib.api.tile.ITileEntity;

import java.util.UUID;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ICBNetTile extends ITileEntity
{
	UUID getUUID();
	boolean isDeviceAvailable(ICBController c);
	void setController(ICBController c);
	ICBController getController();
	void onCBNetworkChanged(BlockDimPos pos);
}