package latmod.silicio.tile;

import ftb.lib.api.tile.TileLM;
import latmod.silicio.api.tile.energy.CapabilitySilEnergyTank;
import latmod.silicio.api.tile.energy.ISilEnergyTank;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileESU extends TileLM
{
	public ISilEnergyTank energyTank;
	
	public TileESU()
	{
		energyTank = new EnergyTankESU(1000000D);
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		tag.setDouble("Energy", energyTank.getEnergy());
	}
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		energyTank.setEnergy(tag.getDouble("Energy"));
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilitySilEnergyTank.ENERGY_TANK_CAPABILITY)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilitySilEnergyTank.ENERGY_TANK_CAPABILITY)
		{
			return (T) energyTank;
		}
		
		return super.getCapability(capability, facing);
	}
	
	public class EnergyTankESU extends SilEnergyTank
	{
		public EnergyTankESU(double max)
		{
			super(max);
		}
		
		@Override
		public double canReceiveEnergy(ISilEnergyTank tank, double e)
		{ return tank instanceof EnergyTankESU ? 0D : e; }
	}
}