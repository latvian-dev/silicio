package latmod.silicio.api.tileentity;

import net.minecraft.util.*;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ICBNetTile
{
	BlockPos getPos();
	void onCBNetworkChanged(BlockPos pos);
	boolean canCBConnect(EnumFacing facing);
}
