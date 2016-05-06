package latmod.silicio.api.tile.energy;

import net.minecraft.util.math.MathHelper;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class SilEnergyTank implements ISilEnergyTank
{
	private double energy;
	private final double maxEnergy;
	public boolean allowToExtractEnergy = true;
	public boolean allowToInjectEnergy = true;
	public boolean energyChanged = false;
	
	public SilEnergyTank(double max)
	{
		maxEnergy = max;
	}
	
	@Override
	public double getEnergy()
	{ return energy; }
	
	@Override
	public void setEnergy(double e)
	{
		double e0 = energy;
		energy = MathHelper.clamp_double(e, 0D, getMaxEnergy());
		
		if(e0 != energy)
		{
			energyChanged = true;
		}
	}
	
	@Override
	public double getMaxEnergy()
	{ return maxEnergy; }
	
	//FIXME
	@Override
	public double injectEnergy(ISilEnergyTank tank, double max, boolean simulate)
	{
		if(!allowToInjectEnergy || tank == this || max <= 0D)
		{ return 0D; }
		
		double e0 = getEnergy();
		double emax = getMaxEnergy();
		
		double d = Math.min(emax - e0, max);
		
		if(d == 0D)
		{ return 0D; }
		
		if(!simulate)
		{
			setEnergy(e0 + d);
			tank.setEnergy(tank.getEnergy() + d);
		}
		
		return 0D;
	}
	
	//FIXME
	@Override
	public double extractEnergy(ISilEnergyTank tank, double max, boolean simulate)
	{
		if(!allowToExtractEnergy || tank == this)
		{
			return 0D;
		}
		
		double e0 = getEnergy();
		
		if(e0 == 0D)
		{
			return 0D;
		}
		
		return 0D;
	}
}