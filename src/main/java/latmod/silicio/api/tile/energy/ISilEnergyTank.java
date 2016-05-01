package latmod.silicio.api.tile.energy;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public interface ISilEnergyTank
{
	double getEnergy();
	double getMaxEnergy();
	void setEnergy(double e);
	void setMaxEnergy(double e);
	double canReceiveEnergy(ISilEnergyTank tank, double e);
	double transferTo(ISilEnergyTank tank, double e, boolean simulate);
}