package latmod.silicio.tile;

import ftb.lib.api.tile.TileLM;
import latmod.silicio.api.tileentity.*;
import net.minecraft.util.*;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileCBNetwork extends TileLM implements ICBNetTile
{
	private ICBController controller;
	
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
	
	public void setController(ICBController c)
	{ controller = c; }
	
	public ICBController getController()
	{ return controller; }
	
	public void onCBNetworkChanged(BlockPos pos)
	{
	}
	
	public boolean canCBConnect(EnumFacing facing)
	{
		return true;
	}
}
