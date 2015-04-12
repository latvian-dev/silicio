package latmod.silicio.tile;

import java.util.List;

import latmod.core.InvUtils;
import latmod.core.tile.*;
import latmod.core.util.*;
import latmod.silicio.item.modules.*;
import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.*;

public class TileCBController extends TileLM implements ICBNetTile, IEnergyHandler, IWailaTile.Body
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
		channels = CBChannel.create(64, CBChannel.Type.GLOBAL);
		prevChannels = CBChannel.create(64, CBChannel.Type.GLOBAL);
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
			
			for(int i = 0; i < network.size(); i++)
			{
				ICBNetTile t = network.get(i);
				
				if(t instanceof TileCBCable) cables++;
			}
			
			otherDevices = network.size() - cables;
			
			info.add("Cables: " + cables);
			if(otherDevices > 0) info.add("Other Devices: " + otherDevices);
			if(!circuitBoards.isEmpty()) info.add("CircuitBoards: " + circuitBoards.size());
			if(!allModules.isEmpty()) info.add("Modules: " + allModules.size());
			if(!invNetwork.isEmpty()) info.add("IInventories: " + invNetwork.size());
			if(!tankNetwork.isEmpty()) info.add("IFluidHandlers: " + tankNetwork.size());
		}
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
			
			for(int i = 0; i < network.size(); i++)
			{
				ICBNetTile ec = network.get(i);
				ec.onNetworkChanged(hasConflict ? null : this);
			}
		}
		
		if(!isServer() || hasConflict) return;
		
		preUpdate();
		
		for(int i = 0; i < network.size(); i++)
			network.get(i).preUpdate();
		
		for(int i = 0; i < allModules.size(); i++)
		{
			CircuitBoard cb = allModules.keys.get(i);
			FastMap<Integer, ICBModule> modules = allModules.values.get(i);
			
			cb.preUpdate();
			
			for(int j = 0; j < modules.size(); j++)
			{
				ICBModule m = modules.values.get(j);
				
				if(m instanceof ISignalProvider)
				{
					ItemStack is = cb.items[modules.keys.get(j)];
					
					((ISignalProvider)m).provideSignals(is, cb);
				}
			}
		}
		
		for(int i = 0; i < allModules.size(); i++)
		{
			CircuitBoard cb = allModules.keys.get(i);
			FastMap<Integer, ICBModule> modules = allModules.values.get(i);
			
			if(cb.cable != null && cb.cable.controller == this)
			for(int j = 0; j < modules.size(); j++)
			{
				ItemStack is = cb.items[modules.keys.get(j)];
				ICBModule m = modules.values.get(j);
				
				m.onUpdate(is, cb);
			}
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
						markDirty();
						
						ICBModule m = modules.values.get(j);
						
						if(m instanceof IToggable)
						{
							ItemStack is = cb.items[modules.keys.get(j)];
							((IToggable)is.getItem()).onChannelToggled(is, cb, channels[k]);
						}
					}
				}
			}
			
			cb.postUpdate();
		}
	}
	
	public void onBroken()
	{
		super.onBroken();
		
		if(!hasConflict) for(int i = 0; i < network.size(); i++)
		{
			ICBNetTile ec = network.get(i);
			TileEntity te = ec.getTile();
			
			if(te != null && !te.isInvalid())
				ec.onNetworkChanged(null);
		}
	}
	
	private void addToList(int x, int y, int z)
	{
		for(int i = 0; i < 6; i++)
		{
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			int px = x + dir.offsetX;
			int py = y + dir.offsetY;
			int pz = z + dir.offsetZ;
			
			TileEntity te = worldObj.getTileEntity(px, py, pz);
			
			if(te != null && !te.isInvalid() && te instanceof ICBNetTile && !network.contains(te))
			{
				if(te != this && te instanceof TileCBController)
				{
					hasConflict = true;
					return;
				}
				
				ICBNetTile ec = (ICBNetTile)te;
				if(ec != null && !network.contains(te))
				{
					network.add(ec);
					
					if(te instanceof TileCBCable)
					{
						for(CircuitBoard cb : ((TileCBCable)te).boards)
						if(cb != null)
						{
							circuitBoards.add(cb);
							
							FastMap<Integer, ICBModule> modules = cb.getAllModules();
							allModules.put(cb, modules);
							
							for(int j = 0; j < modules.size(); j++)
							{
								ICBModule m = modules.values.get(j);
								ItemStack is = cb.items[modules.keys.get(j)];
								
								m.updateInvNet(is, cb, invNetwork);
								m.updateTankNet(is, cb, tankNetwork);
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
}