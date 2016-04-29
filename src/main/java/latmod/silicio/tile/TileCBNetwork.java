package latmod.silicio.tile;

import ftb.lib.api.tile.TileLM;
import latmod.silicio.api.tileentity.CBNetwork;
import latmod.silicio.api.tileentity.ICBController;
import latmod.silicio.api.tileentity.ICBNetTile;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileCBNetwork extends TileLM implements ICBNetTile
{
	private ICBController controller;
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		CBNetwork.load(this);
	}
	
	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		CBNetwork.unload(this);
	}
	
	@Override
	public void setController(ICBController c)
	{ controller = c; }
	
	@Override
	public ICBController getController()
	{ return controller; }
	
	@Override
	public void onCBNetworkChanged(BlockPos pos)
	{
	}
	
	@Override
	public boolean canCBConnect(EnumFacing facing)
	{
		return true;
	}
}
