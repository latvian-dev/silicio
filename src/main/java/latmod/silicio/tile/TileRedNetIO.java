package latmod.silicio.tile;

import java.util.List;

import latmod.core.tile.*;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileRedNetIO extends TileLM implements ICBNetTile, IWailaTile.Body // BlockRedNetIO
{
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()); return true; }
	
	public void preUpdate(TileCBController c)
	{
	}
	
	public void onUpdateCB()
	{
	}
	
	public void onControllerDisconnected()
	{
	}
	
	public boolean isSideEnabled(int side)
	{ return true; }
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
	}
}