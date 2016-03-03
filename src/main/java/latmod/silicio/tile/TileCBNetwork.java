package latmod.silicio.tile;

import ftb.lib.api.tile.TileLM;
import net.minecraft.util.*;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileCBNetwork extends TileLM implements ICBNetTile
{
	public void onLoad()
	{
		super.onLoad();
		CBNetwork.load(this);
	}
	
	public void onChunkUnload()
	{
		super.onChunkUnload();
		CBNetwork.unload(this);
	}
	
	public void onCBNetworkChanged(BlockPos pos)
	{
	}
	
	public boolean canCBConnect(EnumFacing facing)
	{
		return true;
	}
}
