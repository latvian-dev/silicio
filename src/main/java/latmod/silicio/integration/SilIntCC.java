package latmod.silicio.integration;

import latmod.silicio.tile.cb.TileComputerIO;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.*;

public class SilIntCC implements IPeripheralProvider
{
	public static void onLoaded() throws Exception
	{
		ComputerCraftAPI.registerPeripheralProvider(new SilIntCC());
	}
	
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te != null && !te.isInvalid() && te instanceof TileComputerIO)
			return (TileComputerIO)te;
		
		return null;
	}
}