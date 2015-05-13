package latmod.silicio.item.modules.io;

import latmod.silicio.item.modules.IOType;
import latmod.silicio.item.modules.events.EventUpdateModule;
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
	
	public void onUpdate(EventUpdateModule e)
	{
		if(e.cable.isServer() && e.controller.hasEnergy(1) && e.isEnabled(0, -1, true))
		{
			TileEntity te = e.board.getFacingTile();
			
			if(te != null && !te.isInvalid() && te instanceof IEnergyReceiver)
			{
				IEnergyReceiver ie = (IEnergyReceiver)te;
				
				if(ie.canConnectEnergy(ForgeDirection.VALID_DIRECTIONS[e.board.sideOpposite]))
					e.controller.extractEnergy(ie.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[e.board.sideOpposite], Math.min(e.controller.storage.getMaxExtract(), e.controller.storage.getEnergyStored()), false));
			}
		}
	}
}