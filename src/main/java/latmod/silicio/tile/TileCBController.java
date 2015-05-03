package latmod.silicio.tile;

import java.util.List;

import latmod.core.*;
import latmod.core.gui.ContainerEmpty;
import latmod.core.tile.*;
import latmod.core.util.*;
import latmod.silicio.SilItems;
import latmod.silicio.gui.GuiController;
import latmod.silicio.item.IItemCard;
import latmod.silicio.item.modules.ICBModule;
import mcp.mobius.waila.api.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.*;
import cpw.mods.fml.relauncher.*;

public class TileCBController extends TileLM implements ICBNetTile, IEnergyReceiver, IWailaTile.Body, ISecureTile, IGuiTile
{
	public final FastList<ICBNetTile> network;
	private final FastList<ICBNetTile> prevNetwork;
	public final FastList<CircuitBoard> circuitBoards;
	public final FastMap<CircuitBoard, FastMap<Integer, ICBModule>> allModules;
	public final FastList<InvEntry> invNetwork;
	public final FastList<TankEntry> tankNetwork;
	public final CBChannel[] channels;
	public final CBChannel[] prevChannels;
	public boolean energyChanged;
	public EnergyStorage storage;
	private int pNetworkSize = -1;
	public boolean hasConflict = false;
	
	public TileCBController()
	{
		storage = new EnergyStorage(50000000, 500000);
		network = new FastList<ICBNetTile>();
		prevNetwork = new FastList<ICBNetTile>();
		circuitBoards = new FastList<CircuitBoard>();
		allModules = new FastMap<CircuitBoard, FastMap<Integer, ICBModule>>();
		invNetwork = new FastList<InvEntry>();
		tankNetwork = new FastList<TankEntry>();
		channels = CBChannel.create(128);
		prevChannels = CBChannel.create(128);
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		storage.readFromNBT(tag);
		CBChannel.readFromNBT(tag, "Channels", channels);
		hasConflict = tag.hasKey("Conflict");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		storage.writeToNBT(tag);
		CBChannel.writeToNBT(tag, "Channels", channels);
		if(hasConflict) tag.setBoolean("Conflict", true);
	}
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		ICBEnergyTile.Helper.addWaila(storage, info);
		
		if(hasConflict) info.add("Conflicting Controller found!");
		else
		{
			int cables = 0;
			int otherDevices = 0;
			
			for(ICBNetTile t : network)
			{ if(t instanceof TileCBCable) cables++; }
			
			otherDevices = network.size() - cables;
			
			if(cables > 0) info.add("Cables: " + cables);
			if(otherDevices > 0) info.add("Other Devices: " + otherDevices);
			if(!circuitBoards.isEmpty()) info.add("CircuitBoards: " + circuitBoards.size());
			
			int am = 0;
			for(FastMap<Integer, ICBModule> m : allModules) am += m.size();
			if(!allModules.isEmpty()) info.add("Modules: " + am);
			
			if(!invNetwork.isEmpty()) info.add("IInventories: " + invNetwork.size());
			if(!tankNetwork.isEmpty()) info.add("IFluidHandlers: " + tankNetwork.size());
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) LatCoreMC.openGui(ep, this, null); return true; }
	
	public boolean canConnect(ForgeDirection side)
	{ return true; }
	
	public void preUpdate(TileCBController c)
	{
		CBChannel.copy(channels, prevChannels);
		CBChannel.clear(channels);
	}
	
	public void onUnloaded()
	{
		for(ICBNetTile t : network)
			t.onControllerDisconnected();
	}
	
	public void onUpdate()
	{
		if(isServer() && energyChanged && tick % 5 == 0)
		{
			energyChanged = false;
			markDirty();
			
			for(ICBNetTile t : network)
			{
				if(t instanceof ICBEnergyTile)
					((TileEntity)t).markDirty();
			}
		}
		
		// Update network //
		
		boolean pHasConflict = hasConflict;
		hasConflict = false;
		
		prevNetwork.clear();
		prevNetwork.addAll(network);
		
		network.clear();
		circuitBoards.clear();
		allModules.clear();
		invNetwork.clear();
		tankNetwork.clear();
		
		try { onUpdateCB(); }
		catch(Exception e)
		{
			hasConflict = true;
			
			if(worldObj.setBlockToAir(xCoord, yCoord, zCoord))
			{
				e.printStackTrace();
				LatCoreMC.printChat(null, "CBController @ " + LatCore.stripInt(xCoord, yCoord, zCoord) + " crashed!", true);
				InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, new ItemStack(SilItems.b_cbcontroller), 10);
			}
		}
		
		for(ICBNetTile t : prevNetwork)
			t.onControllerDisconnected();
		
		if(pHasConflict != hasConflict)
			markDirty();
	}
	
	public void onUpdateCB()
	{
		addToList(xCoord, yCoord, zCoord);
		network.remove(this);
		
		if(pNetworkSize != network.size())
		{
			pNetworkSize = network.size();
			
			markDirty();
			sendDirtyUpdate();
			
			if(hasConflict) for(ICBNetTile t : network)
				t.onControllerDisconnected();
		}
		
		if(hasConflict) return;
		
		preUpdate(this);
		
		for(ICBNetTile t : network)
		{
			t.preUpdate(this);
			
			if(t instanceof ICBEnergyTile && storage.getEnergyStored() > 0)
			{
				EnergyStorage e = ((ICBEnergyTile)t).getEnergyStorage();
				
				int d = Math.min(e.getMaxReceive(), storage.getMaxExtract());
				d = Math.min(d, Math.min(storage.getEnergyStored(), e.getMaxEnergyStored() - e.getEnergyStored()));
				
				if(d > 0)
				{
					storage.extractEnergy(d, false);
					e.receiveEnergy(d, false);
					energyChanged = true;
				}
			}
		}
		
		if(!isServer()) return;
		
		for(ICBNetTile t : network)
			t.onUpdateCB();
	}
	
	public void onControllerDisconnected()
	{
	}
	
	public void onBroken()
	{
		super.onBroken();
		
		for(ICBNetTile t : network)
			t.onControllerDisconnected();
	}
	
	private void addToList(int x, int y, int z)
	{
		for(int i = 0; i < 6; i++)
		{
			int px = x + Facing.offsetsXForSide[i];
			int py = y + Facing.offsetsYForSide[i];
			int pz = z + Facing.offsetsZForSide[i];
			
			TileEntity te = worldObj.getTileEntity(px, py, pz);
			
			if(te != null && !te.isInvalid() && te instanceof ICBNetTile)
			{
				ICBNetTile ec = (ICBNetTile)te;
				
				if(ec != this && ec instanceof TileCBController)
				{
					hasConflict = true;
					return;
				}
				
				if(ec.isSideEnabled(Facing.oppositeSide[i]) && !network.contains(ec))
				{
					network.add(ec);
					prevNetwork.remove(ec);
					
					if(ec instanceof TileCBCable)
					{
						TileCBCable tc = (TileCBCable)ec;
						for(int b = 0; b < 6; b++) if(tc.boards[b] != null)
						{
							circuitBoards.add(tc.boards[b]);
							
							FastMap<Integer, ICBModule> modules = tc.boards[b].getAllModules();
							allModules.put(tc.boards[b], modules);
							
							for(int j = 0; j < modules.size(); j++)
							{
								ICBModule m = modules.values.get(j);
								int MID = modules.keys.get(j);
								
								m.updateInvNet(tc.boards[b], MID, invNetwork);
								m.updateTankNet(tc.boards[b], MID, tankNetwork);
							}
						}
					}
					
					addToList(px, py, pz);
				}
			}
		}
	}
	
	public boolean canConnectEnergy(ForgeDirection dir)
	{ return true; }
	
	public boolean hasEnergy(int e)
	{ return storage.getEnergyStored() >= e; }
	
	public int receiveEnergy(ForgeDirection dir, int e, boolean simulate)
	{
		int i = storage.receiveEnergy(e, simulate);
		if(i != 0) energyChanged = true; return i;
	}
	
	public int receiveEnergy(int e)
	{ return receiveEnergy(ForgeDirection.UNKNOWN, e, worldObj.isRemote); }
	
	public int extractEnergy(ForgeDirection dir, int e, boolean simulate)
	{
		int i = storage.extractEnergy(e, simulate);
		if(i != 0) energyChanged = true; return i;
	}
	
	public int extractEnergy(int e)
	{ return extractEnergy(ForgeDirection.UNKNOWN, e, worldObj.isRemote); }
	
	public int getEnergyStored(ForgeDirection dir)
	{ return storage.getEnergyStored(); }
	
	public int getMaxEnergyStored(ForgeDirection dir)
	{ return storage.getMaxEnergyStored(); }
	
	public boolean isSideEnabled(int side)
	{ return true; }
	
	public boolean addItem(ItemStack is, boolean simulate)
	{
		if(is == null) return false;
		
		for(InvEntry e : invNetwork.sortToNew(null))
		{
			if(IItemCard.Helper.isValid(e.filter, is))
			{
				if(InvUtils.addSingleItemToInv(is, e.inv, e.side, !simulate))
					return true;
			}
		}
		
		return false;
	}
	
	public boolean requestItem(ItemStack is, boolean simulate)
	{
		if(is == null) return false;
		
		for(InvEntry e : invNetwork.sortToNew(null))
		{
			if(IItemCard.Helper.isValid(e.filter, is))
			{
				int idx = InvUtils.getFirstFilledIndex(e.inv, is, e.side);
				
				if(idx != -1)
				{
					if(!simulate)
					{
						e.inv.setInventorySlotContents(idx, InvUtils.reduceItem(e.inv.getStackInSlot(idx)));
						e.inv.markDirty();
					}
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerEmpty(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiController(new ContainerEmpty(ep, this)); }
}