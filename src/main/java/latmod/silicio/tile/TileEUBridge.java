package latmod.silicio.tile;

import ftb.lib.api.tile.TileLM;
import latmod.silicio.api.tile.energy.CapabilitySilEnergyTank;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileEUBridge extends TileLM
{
	public SilEnergyTank energyTank;
	
	public TileEUBridge()
	{
		energyTank = new SilEnergyTank(1000D);
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		tag.setDouble("SilEnergy", energyTank.getEnergy());
	}
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		energyTank.setEnergy(tag.getDouble("SilEnergy"));
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
	}
}