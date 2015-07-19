package latmod.silicio.tile.cb;

import java.util.List;

import latmod.ftbu.core.LatCoreMC;
import latmod.ftbu.core.inv.LMInvUtils;
import latmod.ftbu.core.tile.*;
import latmod.ftbu.core.waila.WailaDataAccessor;
import latmod.silicio.gui.GuiModuleCopier;
import latmod.silicio.gui.container.ContainerModuleCopier;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.tile.cb.events.EventCB;
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
	
	public EnergyStorage energyStorage;
	public CBNetwork net;
	
	public ItemStack item = null;
	public int progress = 0;
	
	public TileModuleCopier()
	{
		super(3);
		energyStorage = new EnergyStorage(4800);
		net = new CBNetwork();
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		energyStorage.readFromNBT(tag);
		item = LMInvUtils.loadStack(tag, "Item");
		progress = tag.getShort("Prog");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		energyStorage.writeToNBT(tag);
		LMInvUtils.saveStack(tag, "Item", item);
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
				if(items[1].hasTagCompound() && LMInvUtils.itemsEquals(items[0], items[1], false, false))
				{
					item = LMInvUtils.singleCopy(items[0]);
					item.setTagCompound(items[1].getTagCompound());
					items[0] = LMInvUtils.reduceItem(items[0]);
					progress = 1;
					energyStorage.extractEnergy(energyRequired, false);
					markDirty();
				}
			}
		}
		
		if(progress >= maxProg)
		{
			if(server && item != null && LMInvUtils.addSingleItemToInv(item, this, new int[] { 2 }, -1, true))
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
	
	public boolean isSideEnabled(int side)
	{ return true; }
	
	public EnergyStorage getEnergyStorage()
	{ return energyStorage; }
	
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{ ICBEnergyTile.Helper.addWaila(energyStorage, info); }
	
	public float getProgressF()
	{ return progress > 0 ? (progress + 1) / (float)maxProg : 0; }
	
	public int[] getAccessibleSlotsFromSide(int s)
	{ return s == 0 ? new int[] { 2 } : new int[] { 0 }; }
	
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return is != null && is.getItem() instanceof ItemModule; }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return s == 0; }
	
	public CBNetwork getCBNetwork()
	{ return net; }
	
	public void setCBNetwork(CBNetwork n)
	{ net = n; }
	
	public void onCBNetworkEvent(EventCB e)
	{
	}
}