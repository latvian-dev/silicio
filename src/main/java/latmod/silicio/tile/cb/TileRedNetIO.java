package latmod.silicio.tile.cb;

import java.util.List;

import latmod.core.LatCoreMC;
import latmod.core.tile.*;
import latmod.core.util.IntList;
import latmod.silicio.item.modules.events.*;
import mcp.mobius.waila.api.*;
import net.minecraft.block.BlockColored;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class TileRedNetIO extends TileLM implements ICBNetTile, IWailaTile.Body, IGuiTile, ISignalProviderTile // BlockRedNetIO
{
	public int inputSide = 1;
	public IntList inputs = new IntList();
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		inputSide = tag.getByte("Side");
		inputs.setAll(tag.getIntArray("Inputs"));
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setByte("Side", (byte)inputSide);
		tag.setIntArray("Inputs", inputs.toArray());
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer())
		{
			if(LatCoreMC.isWrench(is))
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
			
			LatCoreMC.printChat(null, inputs);
		}
	}
	
	public int[] getOutputValues(ForgeDirection side)
	{ return new int[16]; }
	
	public void provideSignalsTile(EventProvideSignalsTile e)
	{
		for(int i = 0; i < inputs.size(); i++)
			e.setEnabled0(inputs.get(i));
	}
}