package latmod.silicio.item.modules.io;

import latmod.core.util.FastList;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;
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
	
	public void updateTankNet(ItemStack is, CircuitBoard t, FastList<TankEntry> list)
	{
		CBChannel c = getChannel(is, t, 0);
		if(c != CBChannel.NONE && !c.isEnabled()) return;
		
		TileEntity te = t.getFacingTile();
		
		if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
		{
			TankEntry ie = new TankEntry();
			ie.tank = ((IFluidHandler)te);
			ie.side = t.side.getOpposite();
			ie.priority = 1;
			list.add(ie);
		}
	}
}