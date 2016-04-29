package latmod.silicio.api.tileentity;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

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
		notifyNetworkChange(pos);
	}
	
	public static void unload(ICBNetTile t)
	{
		BlockPos pos = t.getTile().getPos();
		network.remove(pos);
		notifyNetworkChange(pos);
	}
	
	private static void notifyNetworkChange(BlockPos pos)
	{
		for(ICBNetTile t : network.values())
		{
			t.onCBNetworkChanged(pos);
		}
	}
	
	public static List<ICBNetTile> getTilesAround(ICBNetTile tile)
	{
		ArrayList<ICBNetTile> list = new ArrayList<>();
		if(network.isEmpty()) return list;
		addTilesTo(tile, list, tile.getTile().getPos());
		return list;
	}
	
	private static void addTilesTo(ICBNetTile original, List<ICBNetTile> list, BlockPos pos)
	{
		for(int i = 0; i < 6; i++)
		{
			BlockPos pos1 = pos.offset(EnumFacing.VALUES[i]);
			ICBNetTile tile = network.get(pos1);
			
			if(tile != null && tile != original && !list.contains(tile) && tile.canCBConnect(EnumFacing.VALUES[i].getOpposite()))
			{
				list.add(tile);
				addTilesTo(original, list, pos1);
			}
		}
	}
}
