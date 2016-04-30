package latmod.silicio.api.tile;

import ftb.lib.BlockDimPos;
import ftb.lib.MathHelperMC;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class CBNetwork
{
	public static final Map<BlockPos, ICBNetTile> network = new HashMap<>();
	
	public static void load(ICBNetTile t)
	{
		BlockPos pos = t.getTile().getPos();
		network.put(pos, t);
		notifyNetworkChange(new BlockDimPos(pos, t.getDimension()), 0D);
	}
	
	public static void unload(ICBNetTile t)
	{
		BlockPos pos = t.getTile().getPos();
		network.remove(pos);
		notifyNetworkChange(new BlockDimPos(pos, t.getDimension()), 0D);
	}
	
	public static void notifyNetworkChange(BlockDimPos pos, double radiusSq)
	{
		Vec3d vec = pos.toVec();
		
		for(ICBNetTile t : network.values())
		{
			if(radiusSq <= 0D || (vec.squareDistanceTo(MathHelperMC.getPosVec(t.getTile().getPos())) <= radiusSq))
			{
				t.onCBNetworkChanged(pos);
			}
		}
	}
	
	public static List<ICBNetTile> getTilesAround(BlockDimPos pos, double radius)
	{
		List<ICBNetTile> list = new ArrayList<>();
		if(network.isEmpty()) return list;
		//FIXME
		return list;
	}
}
