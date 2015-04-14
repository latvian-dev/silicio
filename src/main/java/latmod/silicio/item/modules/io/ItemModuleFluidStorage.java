package latmod.silicio.item.modules.io;

import latmod.core.util.FastList;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemModuleFluidStorage extends ItemModuleIO
{
	public ItemModuleFluidStorage(String s)
	{
		super(s);
		setTextureName("fluid");
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
	
	public void updateTankNet(CircuitBoard cb, int MID, FastList<TankEntry> list)
	{
		CBChannel c = getChannel(cb, MID, 0);
		if(c != CBChannel.NONE && !c.isEnabled()) return;
		
		TileEntity te = cb.getFacingTile();
		
		if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
		{
			TankEntry ie = new TankEntry();
			ie.tank = ((IFluidHandler)te);
			ie.side = cb.side.getOpposite();
			ie.priority = 1;
			list.add(ie);
		}
	}
}