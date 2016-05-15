package latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import latmod.silicio.SilicioCapabilities;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileESU extends TileLM
{
	public SilEnergyTank energyTank;
	
	public TileESU()
	{
		energyTank = new SilEnergyTank(1000000D);
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
	public void onUpdate()
	{
		if(getSide().isServer() && worldObj.getTotalWorldTime() % 20L == 7L && energyTank.energyChanged)
		{
			energyTank.energyChanged = false;
			markDirty();
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == SilicioCapabilities.ENERGY_TANK_CAPABILITY)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == SilicioCapabilities.ENERGY_TANK_CAPABILITY)
		{
			return (T) energyTank;
		}
		
		return super.getCapability(capability, facing);
	}
}