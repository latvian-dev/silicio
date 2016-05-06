package latmod.silicio.api.tile.cb;

import net.minecraft.util.math.BlockPos;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ICBController extends ICBNetTile
{
	boolean getSignalState(int id);
	void addToNetwork(BlockPos pos);
	Collection<BlockPos> getNetwork();
}