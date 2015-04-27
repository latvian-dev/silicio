package latmod.silicio.item.modules.io;

import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyReceiver;

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
	
	public void onUpdate(CircuitBoard cb, int MID)
	{
		if(cb.cable.isServer())
		{
			CBChannel c = getChannel(cb, MID, 0);
			if(c != CBChannel.NONE && !c.isEnabled()) return;
			
			TileEntity te = cb.getFacingTile();
			
			if(cb.cable.controller().hasEnergy(1) && te != null && !te.isInvalid())
			{
				if(te instanceof IEnergyReceiver)
				{
					IEnergyReceiver ie = (IEnergyReceiver)te;
					
					if(ie.canConnectEnergy(ForgeDirection.VALID_DIRECTIONS[cb.sideOpposite]))
					{
						int i = ie.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[cb.sideOpposite], Math.min(cb.cable.controller().storage.getMaxExtract(), cb.cable.controller().storage.getEnergyStored()), false);
						
						if(i != 0)
						{
							cb.cable.controller().storage.extractEnergy(i, false);
							cb.cable.controller().energyChanged = true;
						}
					}
				}
			}
		}
	}
}