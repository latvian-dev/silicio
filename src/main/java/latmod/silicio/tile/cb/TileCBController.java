package latmod.silicio.tile.cb;

import java.util.List;

import latmod.core.*;
import latmod.core.gui.ContainerEmpty;
import latmod.core.tile.*;
import latmod.core.util.*;
import latmod.silicio.SilItems;
import latmod.silicio.gui.GuiController;
import latmod.silicio.item.IItemCard;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.events.*;
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
	public static final int MAX_CHANNEL = 128;
	
	public EnergyStorage storage;
	public final FastList<ICBNetTile> network;
	private final FastList<ICBNetTile> prevNetwork;
	public final FastList<CircuitBoard> circuitBoards;
	public final FastList<ModuleEntry> allModules;
	public final FastList<InvEntry> invNetwork;
	public final FastList<TankEntry> tankNetwork;
	public final IntList channels;
	
	public boolean energyChanged = false;
	private int pNetworkSize = -1;
	public boolean hasConflict = false;
	private int prevEnergyWaila = -1;
	private int currentEnergyWaila = -1;
	
	public TileCBController()
	{
		storage = new EnergyStorage(48000000, 4800);
		network = new FastList<ICBNetTile>();
		prevNetwork = new FastList<ICBNetTile>();
		circuitBoards = new FastList<CircuitBoard>();
		allModules = new FastList<ModuleEntry>();
		invNetwork = new FastList<InvEntry>();
		tankNetwork = new FastList<TankEntry>();
		channels = new IntList();
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		storage.readFromNBT(tag);
		channels.clear();
		channels.addAll(tag.getIntArray("Channels"));
		hasConflict = tag.hasKey("Conflict");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		storage.writeToNBT(tag);
		tag.setIntArray("Channels", channels.toArray());
		if(hasConflict) tag.setBoolean("Conflict", true);
	}
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		ICBEnergyTile.Helper.addWailaWithChange(storage, info, (currentEnergyWaila == -1 || prevEnergyWaila == -1) ? 0 : (currentEnergyWaila - prevEnergyWaila));
		
		if(hasConflict) info.add("Conflicting Controller found!");
		else
		{
			int cables = 0;
			int otherDevices = 0;
			
			for(ICBNetTile t : network)
			{ if(t instanceof TileCBCable) cables++; }
			
			otherDevices = network.size() - cables;
			
			if(cables > 0 || otherDevices > 0) info.add("Cables | Other Devices: " + cables + " | " + otherDevices);
			
			if(!circuitBoards.isEmpty() || !allModules.isEmpty()) info.add("Boards | Modules: " + circuitBoards.size() + " | " + allModules.size());
			if(!invNetwork.isEmpty() || !tankNetwork.isEmpty()) info.add("Chests | Tanks: " + invNetwork.size() + " | " + tankNetwork.size());
			if(!channels.isEmpty()) info.add("Enabled Channels: " + channels.size());
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) LatCoreMC.openGui(ep, this, null); return true; }
	
	public boolean canConnect(ForgeDirection side)
	{ return true; }
	
	public void preUpdate(TileCBController c) { }
	
	public void onUnloaded()
	{
		for(ICBNetTile t : network)
			t.onControllerDisconnected(new EventControllerDisconnected(this, false));
	}
	
	public void onUpdate()
	{
		if(isServer() && energyChanged && tick % 20 == 0)
		{
			energyChanged = false;
			
			prevEnergyWaila = currentEnergyWaila;
			currentEnergyWaila = storage.getEnergyStored();
			
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
			t.onControllerDisconnected(new EventControllerDisconnected(this, false));
		
		if(pHasConflict != hasConflict)
			markDirty();
	}
	
	public void onUpdateCB()
	{
		IntList prevChannels = channels.copy();
		channels.clear();
		
		addToList(xCoord, yCoord, zCoord);
		network.remove(this);
		invNetwork.sort(null);
		tankNetwork.sort(null);
		
		if(pNetworkSize != network.size())
		{
			pNetworkSize = network.size();
			
			markDirty();
			sendDirtyUpdate();
			
			if(hasConflict) for(ICBNetTile t : network)
				t.onControllerDisconnected(new EventControllerDisconnected(this, true));
		}
		
		if(hasConflict) return;
		
		for(ICBNetTile t : network)
		{
			t.onControllerConnected(new EventControllerConnected(this));
			
			if(t instanceof ICBEnergyTile && storage.getEnergyStored() > 0)
			{
				EnergyStorage e = ((ICBEnergyTile)t).getEnergyStorage();
				
				int d = Math.min(e.getMaxReceive(), storage.getMaxExtract());
				d = Math.min(d, Math.min(storage.getEnergyStored(), e.getMaxEnergyStored() - e.getEnergyStored()));
				if(d > 0) e.receiveEnergy(extractEnergy(d), false);
			}
		}
		
		if(!isServer()) return;
		
		for(ModuleEntry me : allModules)
		{
			if(me.item instanceof ISignalProvider)
				((ISignalProvider)me.item).provideSignals(new EventProvideSignals(me));
		}
		
		for(ICBNetTile t : network)
		{
			if(t instanceof ISignalProviderTile)
				((ISignalProviderTile)t).provideSignalsTile(new EventProvideSignalsTile(this));
		}
		
		for(ModuleEntry me : allModules)
			me.item.onUpdate(new EventUpdateModule(me));
		
		for(ICBNetTile t : network)
			t.onUpdateCB();
		
		{
			for(int i = 0; i < channels.size(); i++)
			{
				int ch = channels.get(i);
				if(!prevChannels.contains(ch))
					channelChanged(ch, true);
			}
			
			for(int i = 0; i < prevChannels.size(); i++)
			{
				int ch = prevChannels.get(i);
				if(!channels.contains(ch))
					channelChanged(ch, false);
			}
		}
	}
	
	private void channelChanged(int ch, boolean on)
	{
		LatCoreMC.printChat(null, this + ": " + ch + " - " + on);
		
		for(ModuleEntry me : allModules)
		{
			if(me.item instanceof IToggable)
				((IToggable)me.item).onChannelToggled(new EventChannelToggled(me, ch, on));
		}
		
		for(ICBNetTile t : network)
		{
			if(t instanceof IToggableTile)
				((IToggableTile)t).onChannelToggledTile(new EventChannelToggledTile(this, ch, on));
		}
	}
	
	public void onControllerConnected(EventControllerConnected e)
	{
	}
	
	public void onControllerDisconnected(EventControllerDisconnected e)
	{
	}
	
	public void onBroken()
	{
		for(ICBNetTile t : network)
			t.onControllerDisconnected(new EventControllerDisconnected(this, false));
		
		super.onBroken();
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
							
							for(int mid = 0; mid < tc.boards[b].items.length; mid++)
							{
								if(tc.boards[b].items[mid] != null && tc.boards[b].items[mid].getItem() instanceof ItemModule)
								{
									ModuleEntry me = new ModuleEntry(this, tc.boards[b], mid);
									allModules.add(me);
									
									if(me.item instanceof IInvProvider) ((IInvProvider)me.item).updateInvNet(me, invNetwork);
									if(me.item instanceof ITankProvider) ((ITankProvider)me.item).updateTankNet(me, tankNetwork);
								}
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
	
	public boolean isEnabled(int ch, int ch1, boolean def)
	{
		if(ch == -1) return def;
		if(ch1 != -1 && ch != ch1) return false;
		return channels.contains(ch);
	}
	
	public boolean setEnabled(int ch)
	{
		if(ch == -1) return false;
		if(!channels.contains(ch))
		{ channels.add(ch); return true; }
		return false;
	}
	
	public boolean addItem(ItemStack is, boolean simulate)
	{
		if(is == null) return false;
		
		for(InvEntry e : invNetwork)
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
		
		for(InvEntry e : invNetwork)
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
	
	public static String getChannelName(int i)
	{
		if(i < 0) return "Disabled";
		return (EnumDyeColor.VALUES[i % 16].toString() + " #" + (i / 16 + 1));
	}
	
	public int hashCode()
	{ return LatCore.hashCode(xCoord, yCoord, zCoord); }
}