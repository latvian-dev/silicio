package latmod.silicio.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ftb.lib.api.tile.TileLM;
import latmod.silicio.api.tile.energy.CapabilitySilEnergyTank;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileRFBridge extends TileLM implements IEnergyReceiver
{
	public SilEnergyTank energyTank;
	public RFEnergyTankMirror mirror;
	
	public TileRFBridge()
	{
		energyTank = new SilEnergyTank(4800D);
		mirror = new RFEnergyTankMirror(new EnergyStorage(4800));
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		tag.setDouble("SilEnergy", energyTank.getEnergy());
		mirror.storage.writeToNBT(tag);
	}
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		energyTank.setEnergy(tag.getDouble("SilEnergy"));
		mirror.storage.readFromNBT(tag);
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
	
	@Override
	public void onUpdate()
	{
		if(getSide().isServer() && mirror.getEnergy() > 0D)
		{
			if(mirror.transferTo(energyTank, mirror.getEnergy(), false) > 0D)
			{
			}
		}
	}
	
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{ return mirror.storage.receiveEnergy(maxReceive, simulate); }
	
	@Override
	public int getEnergyStored(EnumFacing from)
	{ return mirror.storage.getEnergyStored(); }
	
	@Override
	public int getMaxEnergyStored(EnumFacing from)
	{ return mirror.storage.getMaxEnergyStored(); }
	
	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{ return true; }
	
	public static class RFEnergyTankMirror extends SilEnergyTank
	{
		public final EnergyStorage storage;
		
		public RFEnergyTankMirror(EnergyStorage s)
		{
			super(s.getMaxEnergyStored());
			storage = s;
		}
	}
}