package latmod.silicio.tile;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TankEntry implements Comparable<TankEntry>
{
	public final IFluidHandler tank;
	public final int side;
	public final ForgeDirection sideF;
	public final int priority;
	public final FluidStack filter;
	
	public TankEntry(IFluidHandler h, int s, int p, FluidStack fs)
	{ tank = h; side = s; priority = p; filter = fs; sideF = ForgeDirection.VALID_DIRECTIONS[side]; }
	
	public int compareTo(TankEntry o)
	{ return Integer.compare(priority, o.priority); }
}