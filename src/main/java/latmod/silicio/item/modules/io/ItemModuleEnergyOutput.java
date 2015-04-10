package latmod.silicio.item.modules.io;

import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

public class ItemModuleEnergyOutput extends ItemModuleIO
{
	public ItemModuleEnergyOutput(String s)
	{
		super(s);
		setTextureName("energy");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.OUTPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
	
	public void onUpdate(ItemStack is, CircuitBoard t)
	{
		if(t.cable.isServer())
		{
			CBChannel c = getChannel(is, t, 0);
			if(c != CBChannel.NONE && !c.isEnabled()) return;
			
			TileEntity te = t.getFacingTile();
			
			if(t.cable.controller.hasEnergy(1) && te != null && !te.isInvalid())
			{
				if(te instanceof IEnergyHandler)
				{
					IEnergyHandler ie = (IEnergyHandler)te;
					
					if(ie.canConnectEnergy(t.side.getOpposite()))
					{
						int i = ie.receiveEnergy(t.side.getOpposite(), Math.min(t.cable.controller.storage.getMaxExtract(), t.cable.controller.storage.getEnergyStored()), false);
						t.cable.controller.storage.extractEnergy(i, false);
					}
				}
			}
		}
	}
}