package latmod.silicio.api.tile.energy;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public interface ISilEnergyTank
{
    /**
     * @return Stored energy
     */
    double getEnergy();
    
    /**
     * Set energy amount. Used by capability storage and other tanks to inject/extract energy
     *
     * @param e New energy amount
     */
    void setEnergy(double e);
    
    /**
     * @return Max stored energy this energy tank can hold
     */
    double getMaxEnergy();
    
    /**
     * @param tank     The tank from which energy is extracted
     * @param max      Max energy to be injected
     * @param simulate If energy injection should only be simulated
     * @return Energy that was injected into this energy tank
     */
    double injectEnergy(ISilEnergyTank tank, double max, boolean simulate);
    
    /**
     * @param tank     The tank from which energy is extracted
     * @param max      Max energy to be injected
     * @param simulate If energy injection should only be simulated
     * @return Energy that was injected into this energy tank
     */
    double extractEnergy(ISilEnergyTank tank, double max, boolean simulate);
}