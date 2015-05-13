package latmod.silicio.tile.cb;

import java.util.List;

import latmod.core.*;
import latmod.core.tile.*;
import latmod.silicio.gui.GuiModuleCopier;
import latmod.silicio.gui.container.ContainerModuleCopier;
import latmod.silicio.item.modules.ItemModule;
import mcp.mobius.waila.api.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.*;

public class TileModuleCopier extends TileInvLM implements IGuiTile, ICBNetTile, ICBEnergyTile, IWailaTile.Body, ISidedInventory
{
	public static final int maxProg = 50;
	public static final int energyRequired = 1200;
	
	public EnergyStorage energyStorage = new EnergyStorage(4800);
	
	public ItemStack item = null;
	public int progress = 0;
	
	public TileModuleCopier()
	{ super(3); }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		energyStorage.readFromNBT(tag);
		item = InvUtils.loadStack(tag, "Item");
		progress = tag.getShort("Prog");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		energyStorage.writeToNBT(tag);
		InvUtils.saveStack(tag, "Item", item);
		tag.setShort("Prog", (short)progress);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) LatCoreMC.openGui(ep, this, null); return true; }
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerModuleCopier(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiModuleCopier(new ContainerModuleCopier(ep, this)); }
	
	public void preUpdate(TileCBController c)
	{
		boolean server = isServer();
		
		if(progress == 0)
		{
			if(items[0] != null && items[1] != null && energyStorage.getEnergyStored() >= energyRequired && items[0].getItem() instanceof ItemModule)
			{
				if(items[1].hasTagCompound() && InvUtils.itemsEquals(items[0], items[1], false, false))
				{
					item = InvUtils.singleCopy(items[0]);
					item.setTagCompound(items[1].getTagCompound());
					items[0] = InvUtils.reduceItem(items[0]);
					progress = 1;
					energyStorage.extractEnergy(energyRequired, false);
					markDirty();
				}
			}
		}
		
		if(progress >= maxProg)
		{
			if(server && item != null && InvUtils.addSingleItemToInv(item, this, new int[] { 2 }, -1, true))
			{
				item = null;
				progress = 0;
				markDirty();
			}
		}
		else if(progress >= 1)
			progress++;
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
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{ ICBEnergyTile.Helper.addWaila(energyStorage, info); }
	
	public float getProgressF()
	{ return progress > 0 ? (progress + 1) / (float)maxProg : 0; }
	
	public int[] getAccessibleSlotsFromSide(int s)
	{ return s == 0 ? new int[] { 2 } : new int[] { 0 }; }
	
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return is != null && is.getItem() instanceof ItemModule; }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return s == 0; }
}