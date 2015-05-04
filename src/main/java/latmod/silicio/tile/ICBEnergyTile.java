package latmod.silicio.tile;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import cofh.api.energy.EnergyStorage;

public interface ICBEnergyTile extends ICBNetTile
{
	public EnergyStorage getEnergyStorage();
	
	public static class Helper
	{
		public static void addWaila(EnergyStorage storage, List<String> info)
		{ info.add("Energy: " + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF"); }
		
		public static void addWailaWithChange(EnergyStorage storage, List<String> info, int change)
		{
			String s = (change == 0) ? "+0" : ((change > 0) ? (EnumChatFormatting.GREEN + "+" + change) : (EnumChatFormatting.RED + "" + change));
			info.add("Energy: " + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF [" + s + EnumChatFormatting.WHITE + "]");
		}
	}
}