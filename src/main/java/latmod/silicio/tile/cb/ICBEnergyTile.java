package latmod.silicio.tile.cb;

import cofh.api.energy.EnergyStorage;

import java.util.List;

public interface ICBEnergyTile extends ICBNetTile
{
	public EnergyStorage getEnergyStorage();
	
	public static class Helper
	{
		public static void addWaila(EnergyStorage storage, List<String> info)
		{ info.add("Energy: " + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF"); }
	}
}