package latmod.silicio.tile;

import java.util.List;

import cofh.api.energy.EnergyStorage;

public interface ICBEnergyTile extends ICBNetTile
{
	public EnergyStorage getEnergyStorage();
	
	public static class Helper
	{
		public static void addWaila(EnergyStorage storage, List<String> info)
		{ info.add("Energy: " + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF"); }
	}
}