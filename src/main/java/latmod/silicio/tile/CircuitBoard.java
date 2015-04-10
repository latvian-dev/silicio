package latmod.silicio.tile;
import latmod.core.InvUtils;
import latmod.core.util.FastMap;
import latmod.silicio.item.modules.ICBModule;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class CircuitBoard implements IInventory
{
	public final TileCBCable cable;
	public final ForgeDirection side;
	
	public ItemStack items[] = new ItemStack[12];
	public boolean dropItems = true;
	public long tick = 0L;
	public boolean redstoneOut = false;
	private Boolean prevRedstoneOut = null;
	public final CBChannel[] channels;
	public final CBChannel[] prevChannels;
	
	public CircuitBoard(TileCBCable t, ForgeDirection f)
	{
		cable = t;
		side = f;
		channels = CBChannel.create(16, CBChannel.Type.LOCAL);
		prevChannels = CBChannel.create(16, CBChannel.Type.LOCAL);
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		InvUtils.readItemsFromNBT(items, tag, "Items");
		tick = tag.getLong("Tick");
		redstoneOut = tag.getBoolean("RSOut");
		CBChannel.readFromNBT(tag, "Channels", channels);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		if(items != null) InvUtils.writeItemsToNBT(items, tag, "Items");
		tag.setLong("Tick", tick);
		tag.setBoolean("RSOut", redstoneOut);
		CBChannel.writeToNBT(tag, "Channels", channels);
	}
	
	public FastMap<Integer, ICBModule> getAllModules()
	{
		FastMap<Integer, ICBModule> m = new FastMap<Integer, ICBModule>();
		
		for(int i = 0; i < items.length; i++)
		{
			if(items[i] != null && items[i].getItem() instanceof ICBModule)
				m.put(i, (ICBModule) items[i].getItem());
		}
		
		return m;
	}
	
	public void preUpdate()
	{
		redstoneOut = false;
		CBChannel.copy(channels, prevChannels);
		CBChannel.clear(channels);
	}
	
	public void postUpdate()
	{
		if(prevRedstoneOut == null || prevRedstoneOut != redstoneOut)
		{
			prevRedstoneOut = redstoneOut;
			cable.markDirty();
			cable.getWorldObj().notifyBlocksOfNeighborChange(cable.xCoord, cable.yCoord, cable.zCoord, cable.blockType);
		}
		
		/*
		
		for(int i = 0; i < items.length; i++)
		{
			if(items[i] != null && items[i].getItem() instanceof IToggable)
				;
		}
		
		for(int i = 0; i < allModules.size(); i++)
		{
			CircuitBoard cb = allModules.keys.get(i);
			FastMap<Integer, ICBModule> modules = allModules.values.get(i);
			
			for(int j = 0; j < modules.size(); j++)
			{
				ICBModule m = modules.values.get(j);
				
				if(m instanceof IToggable)
				{
					ItemStack is = cb.items[modules.keys.get(j)];
					
					for(int k = 0; k < channels.length; k++)
					{
						if(prevChannels[k].isEnabled() != channels[k].isEnabled())
						{
							((IToggable)is.getItem()).onChannelToggled(is, cb, channels[k]);
							markDirty();
						}
					}
				}
			}
		}*/
		
		tick++;
	}
	
	public TileEntity getFacingTile()
	{
		TileEntity te = cable.getWorldObj().getTileEntity(cable.xCoord + side.offsetX, cable.yCoord + side.offsetY, cable.zCoord + side.offsetZ);
		return (te == null || te.isInvalid()) ? null : te;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TileEntity> T getFacingTileT(Class<? extends T> c)
	{
		TileEntity te = getFacingTile();
		if(te != null && c.isAssignableFrom(te.getClass())) return (T)te;
		return null;
	}
	
	public Block getFacingBlock()
	{ return cable.getWorldObj().getBlock(cable.xCoord + side.offsetX, cable.yCoord + side.offsetY, cable.zCoord + side.offsetZ); }
	
	public int getSizeInventory()
	{ return items.length; }
	
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }
	
	public ItemStack decrStackSize(int slot, int amt)
	{ return InvUtils.decrStackSize(this, slot, amt); }
	
	public ItemStack getStackInSlotOnClosing(int i)
	{ return InvUtils.getStackInSlotOnClosing(this, i); }
	
	public void setInventorySlotContents(int i, ItemStack is)
	{ items[i] = is; }
	
	public String getInventoryName()
	{ return "Circuit Board"; }
	
	public boolean hasCustomInventoryName()
	{ return true; }
	
	public int getInventoryStackLimit()
	{ return 1; }
	
	public void markDirty()
	{ cable.markDirty(); }
	
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return false; }
	
	public void openInventory() { }
	public void closeInventory() { }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
}