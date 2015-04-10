package latmod.silicio.item.modules.io;

import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

public class ItemModuleEnergyInput extends ItemModuleIO
{
	public ItemModuleEnergyInput(String s)
	{
		super(s);
		setTextureName("energy");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
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
			
			int needed = t.cable.controller.storage.getMaxEnergyStored() - t.cable.controller.storage.getEnergyStored();
			
			if(needed > 0)
			{
				TileEntity te = t.getFacingTile();
				
				if(te != null && !te.isInvalid())
				{
					if(te instanceof IEnergyHandler)
					{
						IEnergyHandler ie = (IEnergyHandler)te;
						
						if(ie.canConnectEnergy(t.side.getOpposite()))
						{
							int i = ie.extractEnergy(t.side.getOpposite(), needed, false);
							t.cable.controller.receiveEnergy(i);
						}
					}
				}
			}
		}
	}
}