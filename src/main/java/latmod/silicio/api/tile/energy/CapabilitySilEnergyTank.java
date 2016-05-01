package latmod.silicio.api.tile.energy;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class CapabilitySilEnergyTank
{
	@CapabilityInject(ISilEnergyTank.class)
	public static Capability<ISilEnergyTank> ENERGY_TANK_CAPABILITY = null;
	
	public static void register()
	{
		CapabilityManager.INSTANCE.register(ISilEnergyTank.class, new Capability.IStorage<ISilEnergyTank>()
		{
			@Override
			public NBTBase writeNBT(Capability<ISilEnergyTank> capability, ISilEnergyTank instance, EnumFacing side)
			{ return new NBTTagDouble(instance.getEnergy()); }
			
			@Override
			public void readNBT(Capability<ISilEnergyTank> capability, ISilEnergyTank instance, EnumFacing side, NBTBase base)
			{ instance.setEnergy(((NBTTagDouble) base).getDouble()); }
		}, new Callable<ISilEnergyTank>()
		{
			@Override
			public ISilEnergyTank call() throws Exception
			{ return new SilEnergyTank(10000D); }
		});
	}
}