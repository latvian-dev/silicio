package latmod.silicio.tile.cb;

import java.util.*;

import latmod.core.*;
import latmod.core.tile.*;
import latmod.core.util.IntList;
import latmod.silicio.item.modules.events.*;
import mcp.mobius.waila.api.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class TileRedNetIO extends TileLM implements ICBNetTile, IWailaTile.Body, IGuiTile, ISignalProviderTile, IToggableTile // BlockRedNetIO
{
	public int inputSide = 1;
	public IntList inputs = new IntList();
	public int[] linked = new int[16];
	
	public TileRedNetIO()
	{ Arrays.fill(linked, -1); }
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		inputSide = tag.getByte("Side");
		inputs.setAll(tag.getIntArray("Inputs"));
		linked = tag.getIntArray("Linked");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setByte("Side", (byte)inputSide);
		tag.setIntArray("Inputs", inputs.toArray());
		tag.setIntArray("Linked", linked);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer())
		{
			if(InvUtils.isWrench(is))
			{ if(inputSide != side) { inputSide = side; markDirty(); } }
			else LatCoreMC.openGui(ep, this, null);
		}
		
		return true;
	}
	
	public void onControllerConnected(EventControllerConnected e)
	{
	}
	
	public void onControllerDisconnected(EventControllerDisconnected e)
	{
	}
	
	public void onUpdateCB()
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
		if(inputSide == side.ordinal() && inputValues.length == 16)
		{
			inputs.clear();
			
			for(int i = 0; i < 16; i++)
				if(inputValues[i] > 0) inputs.add(i);
			
			//LatCoreMC.printChat(null, inputs);
		}
	}
	
	public int[] getOutputValues(ForgeDirection side)
	{
		int[] out = new int[16];
		
		for(int i = 0; i < 16; i++)
		{
			
		}
		
		return out;
	}
	
	public void provideSignalsTile(EventProvideSignalsTile e)
	{
		for(int i = 0; i < inputs.size(); i++)
		{
			int j = inputs.get(i);
			if(j >= 0 && j < linked.length)
				e.setEnabled0(linked[j]);
		}
	}
	
	public void onChannelToggledTile(EventChannelToggledTile e)
	{
	}
}