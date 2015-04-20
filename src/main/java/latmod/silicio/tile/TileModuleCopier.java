package latmod.silicio.tile;

import cofh.api.energy.EnergyStorage;
import latmod.core.*;
import latmod.core.tile.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class TileModuleCopier extends TileInvLM implements IGuiTile, ICBNetTile, ICBEnergyTile
{
	public EnergyStorage energyStorage = new EnergyStorage(12000);
	
	public TileModuleCopier()
	{ super(3); }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		energyStorage.readFromNBT(tag);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		energyStorage.writeToNBT(tag);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) LatCoreMC.openGui(ep, this, null); return true; }
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return null; }
	
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
	
	public EnergyStorage getEnergyStorage()
	{ return energyStorage; }
}