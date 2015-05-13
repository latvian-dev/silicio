package latmod.silicio.tile.cb;

import java.util.List;

import latmod.core.LatCoreMC;
import latmod.core.tile.*;
import latmod.core.util.LatCore;
import latmod.silicio.item.modules.events.EventProvideSignalsTile;
import mcp.mobius.waila.api.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class TileRedNetIO extends TileLM implements ICBNetTile, IWailaTile.Body, IGuiTile, ISignalProviderTile // BlockRedNetIO
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
	{ if(isServer()) LatCoreMC.openGui(ep, this, null); return true; }
	
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
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return null; }

	public void onInputsChanged(ForgeDirection side, int[] inputValues)
	{
		LatCoreMC.printChat(null, LatCore.stripInt(inputValues));
	}
	
	public int[] getOutputValues(ForgeDirection side)
	{ return new int[16]; }
	
	public void provideSignalsTile(EventProvideSignalsTile e)
	{
	}
}