package latmod.silicio.tile.cb;

import java.util.List;

import cofh.api.energy.*;
import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import ftb.lib.api.gui.IGuiTile;
import ftb.lib.item.LMInvUtils;
import latmod.ftbu.api.tile.*;
import latmod.ftbu.util.gui.ContainerEmpty;
import latmod.ftbu.waila.WailaDataAccessor;
import latmod.lib.*;
import latmod.silicio.SilItems;
import latmod.silicio.gui.GuiController;
import latmod.silicio.item.IItemCard;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.cb.events.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCBController extends TileBasicCBNetTile implements IEnergyReceiver, IWailaTile.Body, ISecureTile, IGuiTile // CBNetwork
{
	public static final int MAX_CHANNEL = 128;
	
	public EnergyStorage storage;
	public final IntList prevChannels, channels;
	
	public boolean energyChanged = false;
	private int pNetworkSize = -1;
	
	public TileCBController()
	{
		storage = new EnergyStorage(48000000, 4800);
		prevChannels = new IntList();
		channels = new IntList();
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		storage.readFromNBT(tag);
		channels.setAll(tag.getIntArray("Channels"));
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		storage.writeToNBT(tag);
		tag.setIntArray("Channels", channels.toArray());
	}
	
	public void readTileClientData(NBTTagCompound tag)
	{
		net.hasConflict = tag.hasKey("Conflict");
	}
	
	public void writeTileClientData(NBTTagCompound tag)
	{
		if(net.hasConflict) tag.setBoolean("Conflict", true);
	}
	
	public void readTileServerData(NBTTagCompound tag)
	{
		prevChannels.setAll(tag.getIntArray("PChannels"));
	}
	
	public void writeTileServerData(NBTTagCompound tag)
	{
		tag.setIntArray("PChannels", prevChannels.toArray());
	}
	
	public CBNetwork getCBNetwork()
	{ return net; }
	
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{
		ICBEnergyTile.Helper.addWaila(storage, info);
		
		if(net.hasConflict) info.add("Conflicting Controller found!");
		else
		{
			int cables = 0;
			int otherDevices = 0;
			
			for(ICBNetTile t : net.tiles)
			{ if(t instanceof TileCBCable) cables++; }
			
			otherDevices = net.tiles.size() - cables;
			
			if(cables > 0 || otherDevices > 0) info.add("Cables | Other Devices: " + cables + " | " + otherDevices);
			
			if(!net.circuitBoards.isEmpty() || !net.allModules.isEmpty()) info.add("Boards | Modules: " + net.circuitBoards.size() + " | " + net.allModules.size());
			if(!net.invNetwork.isEmpty() || !net.tankNetwork.isEmpty()) info.add("Chests | Tanks: " + net.invNetwork.size() + " | " + net.tankNetwork.size());
			if(!channels.isEmpty()) info.add("Enabled Channels: " + channels.size());
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) FTBLib.openGui(ep, this, null); return true; }
	
	public boolean canConnect(ForgeDirection side)
	{ return true; }
	
	public void preUpdate(TileCBController c) { }
	
	public void onUnloaded()
	{
		CBNetwork.notifyNetwork(worldObj, xCoord, yCoord, zCoord);
	}
	
	public void onUpdate()
	{
		if(isServer() && energyChanged && tick % 20 == 0)
		{
			energyChanged = false;
			
			markDirty();
			
			for(ICBNetTile t : net.tiles)
			{
				if(t instanceof ICBEnergyTile)
					((TileEntity)t).markDirty();
			}
		}
		
		// Update network //
		
		boolean pHasConflict = net.hasConflict;
		
		try
		{
			CBNetwork.update(net, worldObj, xCoord, yCoord, zCoord);
			onUpdateCB();
		}
		catch(Exception e)
		{
			net.hasConflict = true;
			
			if(isServer() && worldObj.setBlockToAir(xCoord, yCoord, zCoord))
			{
				e.printStackTrace();
				FTBLib.printChat(BroadcastSender.inst, "CBController @ " + LMStringUtils.stripI(xCoord, yCoord, zCoord) + " crashed!");
				LMInvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, new ItemStack(SilItems.b_cbcontroller), 10);
			}
		}
		
		//for(ICBNetTile t : prevNetwork)
		//	t.onCBNetworkEvent(new EventControllerDisconnected(getCBNetwork(), false));
		
		if(pHasConflict != net.hasConflict)
			markDirty();
	}
	
	public void onUpdateCB()
	{
		IntList prevChannels = channels.copy();
		channels.clear();
		
		if(pNetworkSize != net.tiles.size())
		{
			pNetworkSize = net.tiles.size();
			
			markDirty();
			sendDirtyUpdate();
			
			if(net.hasConflict) CBNetwork.notifyNetwork(worldObj, xCoord, yCoord, zCoord);
		}
		
		if(net.hasConflict) return;
		
		CBNetwork.notifyNetwork(worldObj, xCoord, yCoord, zCoord);
		
		for(ICBNetTile t : net.tiles)
		{
			if(t instanceof ICBEnergyTile && storage.getEnergyStored() > 0)
			{
				EnergyStorage e = ((ICBEnergyTile)t).getEnergyStorage();
				
				int d = Math.min(e.getMaxReceive(), storage.getMaxExtract());
				d = Math.min(d, Math.min(storage.getEnergyStored(), e.getMaxEnergyStored() - e.getEnergyStored()));
				if(d > 0) e.receiveEnergy(extractEnergy(d), false);
			}
		}
		
		if(!isServer()) return;
		
		for(ModuleEntry me : net.allModules)
		{
			if(me.item instanceof ISignalProvider)
				((ISignalProvider)me.item).provideSignals(new EventProvideSignals(me));
		}
		
		for(ICBNetTile t : net.tiles)
		{
			if(t instanceof ISignalProviderTile)
				((ISignalProviderTile)t).provideSignalsTile(new EventProvideSignalsTile(getCBNetwork()));
		}
		
		for(ModuleEntry me : net.allModules)
			me.item.onUpdate(new EventUpdateModule(me));
		
		for(ICBNetTile t : net.tiles)
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
		FTBLib.printChat(null, this + ": " + ch + " - " + on);
		
		for(ModuleEntry me : net.allModules)
		{
			if(me.item instanceof IToggable)
				((IToggable)me.item).onChannelToggled(new EventChannelToggled(me, ch, on));
		}
		
		net.sendEvent(new EventChannelToggledTile(getCBNetwork(), ch, on));
	}
	
	public void onBroken()
	{
		net.notifyNetwork();
		super.onBroken();
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
		
		for(InvEntry e : net.invNetwork)
		{
			if(IItemCard.Helper.isValid(e.filter, is))
			{
				if(LMInvUtils.addSingleItemToInv(is, e.inv, e.side, !simulate))
					return true;
			}
		}
		
		return false;
	}
	
	public boolean requestItem(ItemStack is, boolean simulate)
	{
		if(is == null) return false;
		
		for(InvEntry e : net.invNetwork)
		{
			if(IItemCard.Helper.isValid(e.filter, is))
			{
				int idx = LMInvUtils.getFirstFilledIndex(e.inv, is, e.side);
				
				if(idx != -1)
				{
					if(!simulate)
					{
						e.inv.setInventorySlotContents(idx, LMInvUtils.reduceItem(e.inv.getStackInSlot(idx)));
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
	
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return security.canInteract(ep); }
	
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }
}