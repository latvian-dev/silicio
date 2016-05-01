package latmod.silicio.api.tile.energy;

import net.minecraft.util.math.MathHelper;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class SilEnergyTank implements ISilEnergyTank
{
	private double energy, maxEnergy;
	
	public SilEnergyTank(double max)
	{
		maxEnergy = max;
	}
	
	@Override
	public double getEnergy()
	{ return energy; }
	
	@Override
	public double getMaxEnergy()
	{ return maxEnergy; }
	
	@Override
	public void setEnergy(double e)
	{ energy = MathHelper.clamp_double(e, 0D, getMaxEnergy()); }
	
	@Override
	public void setMaxEnergy(double e)
	{ maxEnergy = Math.max(0D, e); }
	
	@Override
	public double canReceiveEnergy(ISilEnergyTank tank, double e)
	{ return e; }
	
	@Override
	public double transferTo(ISilEnergyTank tank, double e, boolean simulate)
	{
		double e0 = getEnergy();
		if(e0 == 0D) { return 0D; }
		
		double e1 = tank.getEnergy();
		
		double d = Math.min(e0, tank.getMaxEnergy() - e1);
		
		d = tank.canReceiveEnergy(this, d);
		
		if(d == 0D) { return 0D; }
		
		if(!simulate)
		{
			setEnergy(e0 - d);
			tank.setEnergy(e1 + d);
		}
		
		return d;
	}
}