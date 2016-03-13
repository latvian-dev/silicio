package latmod.silicio.api.tileentity;

import ftb.lib.api.tile.ITileEntity;
import net.minecraft.util.*;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ICBNetTile extends ITileEntity
{
	void setController(ICBController c);
	ICBController getController();
	void onCBNetworkChanged(BlockPos pos);
	boolean canCBConnect(EnumFacing facing);
}