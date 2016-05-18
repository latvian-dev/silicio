package latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import latmod.silicio.api.SilCapabilities;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileCCBridge extends TileLM
{
	public SilEnergyTank energyTank;
	
	public TileCCBridge()
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
		if(capability == SilCapabilities.ENERGY_TANK)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == SilCapabilities.ENERGY_TANK)
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