package latmod.silicio.api.tile.cb;

import com.feed_the_beast.ftbl.util.MathHelperMC;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.05.2016.
 */
public class CBHelper
{
	public static ICBController linkWithClosestController(World w, BlockPos pos)
	{
		if(w.isRemote)
		{
			return null;
		}
		
		ICBController controller = null;
		double lastDistSq = Double.POSITIVE_INFINITY;
		
		for(TileEntity te : w.loadedTileEntityList)
		{
			if(te instanceof ICBController)
			{
				double distSq = MathHelperMC.getDistanceSq(pos, te.getPos());
				
				if(distSq <= 400D && lastDistSq > distSq)
				{
					lastDistSq = distSq;
					controller = (ICBController) te;
				}
			}
		}
		
		if(controller != null)
		{
			controller.addToNetwork(pos);
		}
		
		return controller;
	}
}