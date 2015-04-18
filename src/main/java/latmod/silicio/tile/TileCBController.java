package latmod.silicio.tile;

import java.util.List;

import latmod.core.InvUtils;
import latmod.core.tile.*;
import latmod.core.util.*;
import latmod.silicio.item.modules.*;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.*;

public class TileCBController extends TileLM implements ICBNetTile, IEnergyHandler, IWailaTile.Body, ISecureTile
{
	public final FastList<ICBNetTile> network;
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
	
	public InvEntry getInventoryFor(ItemStack is)
	{
		invNetwork.sort(new InvComparator(is));
		
		for(int i = 0; i < invNetwork.size(); i++)
		{
			InvEntry e = invNetwork.get(i);
			
			if(e != null)
			{
				if(InvUtils.getFirstIndexWhereFits(e.inv, is, e.side) != -1)
					return e;
			}
		}
		
		return null;
	}
	
	public InvEntry getTankFor(ItemStack is)
	{
		invNetwork.sort(new InvComparator(is));
		
		for(int i = 0; i < invNetwork.size(); i++)
		{
			InvEntry e = invNetwork.get(i);
			
			if(e != null)
			{
				if(InvUtils.getFirstIndexWhereFits(e.inv, is, e.side) != -1)
					return e;
			}
		}
		
		return null;
	}
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
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
	{
		return true;
	}
	
	public boolean canConnect(ForgeDirection side)
	{ return true; }
	
	public void preUpdate()
	{
		CBChannel.copy(channels, prevChannels);
		CBChannel.clear(channels);
	}
	
	public void onUpdate()
	{
		if(isServer() && energyChanged && tick % 5 == 0)
		{
			energyChanged = false;
			markDirty();
		}
		
		// Update network //
		
		boolean pHasConflict = hasConflict;
		hasConflict = false;
		
		network.clear();
		circuitBoards.clear();
		allModules.clear();
		invNetwork.clear();
		tankNetwork.clear();
		
		addToList(xCoord, yCoord, zCoord);
		network.remove(this);
		
		if(pHasConflict != hasConflict)
			markDirty();
		
		if(pNetworkSize != network.size())
		{
			pNetworkSize = network.size();
			
			markDirty();
			sendDirtyUpdate();
			
			for(ICBNetTile t : network)
				t.onNetworkChanged(hasConflict ? null : this);
		}
		
		if(!isServer() || hasConflict) return;
		
		preUpdate();
		
		for(ICBNetTile t : network)
			t.preUpdate();
		
		for(int i = 0; i < allModules.size(); i++)
		{
			CircuitBoard cb = allModules.keys.get(i);
			FastMap<Integer, ICBModule> modules = allModules.values.get(i);
			
			cb.preUpdate();
			
			for(int j = 0; j < modules.size(); j++)
			{
				ICBModule m = modules.values.get(j);
				
				if(m instanceof ISignalProvider)
					((ISignalProvider)m).provideSignals(cb, modules.keys.get(j));
			}
		}
		
		for(int i = 0; i < allModules.size(); i++)
		{
			CircuitBoard cb = allModules.keys.get(i);
			FastMap<Integer, ICBModule> modules = allModules.values.get(i);
			
			if(cb.cable != null && cb.cable.controller == this)
			for(int j = 0; j < modules.size(); j++)
				modules.values.get(j).onUpdate(cb, modules.keys.get(j));
		}
		
		for(int i = 0; i < allModules.size(); i++)
		{
			CircuitBoard cb = allModules.keys.get(i);
			FastMap<Integer, ICBModule> modules = allModules.values.get(i);
			
			for(int j = 0; j < modules.size(); j++)
			{
				for(int k = 0; k < channels.length; k++)
				{
					if(prevChannels[k].isEnabled() != channels[k].isEnabled())
					{
						ICBModule m = modules.values.get(j);
						
						if(m instanceof IToggable)
							((IToggable)m).onChannelToggled(cb, modules.keys.get(j), channels[k]);
						
						markDirty();
					}
				}
			}
			
			cb.postUpdate();
		}
	}
	
	public void onBroken()
	{
		super.onBroken();
		
		if(!hasConflict) for(ICBNetTile t : network)
			t.onNetworkChanged(null);
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
				
				if(ec.isDisabled(Facing.oppositeSide[i])) continue;
				
				if(!network.contains(te))
				{
					network.add(ec);
					
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
	
	public void onNetworkChanged(TileCBController c)
	{
	}
	
	public void onLoaded()
	{
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
	
	public boolean isDisabled(int side)
	{ return false; }
}