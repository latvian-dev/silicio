package latmod.silicio.api.modules;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

/**
 * Created by LatvianModder on 04.05.2016.
 */
public class CapabilityModule
{
	@CapabilityInject(Module.class)
	public static Capability<Module> MODULE_CAPABILITY = null;
	
	public static void init()
	{
		CapabilityManager.INSTANCE.register(Module.class, new Capability.IStorage<Module>()
		{
			@Override
			public NBTBase writeNBT(Capability<Module> capability, Module instance, EnumFacing side)
			{ return null; }
			
			@Override
			public void readNBT(Capability<Module> capability, Module instance, EnumFacing side, NBTBase base)
			{ }
		}, new Callable<Module>()
		{
			@Override
			public Module call() throws Exception
			{ return null; }
		});
	}
}