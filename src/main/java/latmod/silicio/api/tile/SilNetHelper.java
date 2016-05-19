package latmod.silicio.api.tile;

import com.feed_the_beast.ftbl.util.MathHelperMC;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.05.2016.
 */
public class SilNetHelper
{
    public static ISilNetController linkWithClosestController(World w, BlockPos pos)
    {
        if(w.isRemote)
        {
            return null;
        }

        ISilNetController controller = null;
        double lastDistSq = Double.POSITIVE_INFINITY;

        for(TileEntity te : w.loadedTileEntityList)
        {
            if(te instanceof ISilNetController)
            {
                double distSq = MathHelperMC.getDistanceSq(pos, te.getPos());

                if(distSq <= 400D && lastDistSq > distSq)
                {
                    lastDistSq = distSq;
                    controller = (ISilNetController) te;
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