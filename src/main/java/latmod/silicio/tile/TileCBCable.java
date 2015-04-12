package latmod.silicio.tile;
import java.util.Arrays;

import latmod.core.*;
import latmod.core.mod.LC;
import latmod.core.tile.*;
import latmod.core.util.MathHelperLM;
import latmod.silicio.*;
import latmod.silicio.gui.*;
import latmod.silicio.gui.container.*;
import latmod.silicio.item.modules.ICBModule;
import latmod.silicio.item.modules.io.ItemModuleEnergyInput;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.*;

public class TileCBCable extends TileLM implements IPaintable, ICBNetTile, IGuiTile, IEnergyReceiver
{
	public static final String ACTION_SET_CHANNEL = "setCBChannel";
	
	public final CircuitBoard[] boards = new CircuitBoard[6];
	public final Paint[] paint = new Paint[6];
	public boolean hasCover;
	public TileCBController controller;
	public final CBChannel[] channels;
	public final CBChannel[] prevChannels;
	private final boolean[] canReceive;
	
	public TileCBCable()
	{
		channels = CBChannel.create(16, CBChannel.Type.LOCAL);
		prevChannels = CBChannel.create(16, CBChannel.Type.LOCAL);
		canReceive = new boolean[6];
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void preUpdate()
	{
		CBChannel.copy(channels, prevChannels);
		CBChannel.clear(channels);
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		Arrays.fill(boards, null);
		
		NBTTagList l = (NBTTagList)tag.getTag("Boards");
		
		if(l != null && l.tagCount() > 0)
		for(int i = 0; i < l.tagCount(); i++)
		{
			NBTTagCompound tag1 = l.getCompoundTagAt(i);
			int id = tag1.getByte("ID");
			boards[id] = new CircuitBoard(this, MathHelperLM.getDir(id));
			boards[id].readTileData(tag1);
		}
		
		hasCover = tag.getBoolean("HasCover");
		Paint.readFromNBT(tag, "Paint", paint);
		CBChannel.readFromNBT(tag, "Channels", channels);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		NBTTagList l = new NBTTagList();
		
		for(int i = 0; i < boards.length; i++)
		if(boards[i] != null)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			boards[i].writeTileData(tag1);
			tag1.setByte("ID", (byte)i);
			l.appendTag(tag1);
		}
		
		if(l.tagCount() > 0)
			tag.setTag("Boards", l);
		
		tag.setBoolean("HasCover", hasCover);
		Paint.writeToNBT(tag, "Paint", paint);
		CBChannel.writeToNBT(tag, "Channels", channels);
	}
	
	public boolean setPaint(PaintData p)
	{
		if(p.player.isSneaking())
		{
			for(int i = 0; i < 6; i++)
				paint[i] = p.paint;
			markDirty();
			return true;
		}
		
		if(p.canReplace(paint[p.side]))
		{
			paint[p.side] = p.paint;
			markDirty();
			return true;
		}
		
		return false;
	}
	
	public static boolean connectCable(TileCBCable c, ForgeDirection f)
	{
		if(c.boards[f.ordinal()] != null) return true;
		TileEntity te = c.worldObj.getTileEntity(c.xCoord + f.offsetX, c.yCoord + f.offsetY, c.zCoord + f.offsetZ);
		return (te != null && te instanceof TileCBController || te instanceof TileCBCable);
	}
	
	public void onNetworkChanged(TileCBController c)
	{
		controller = c;
		
		if(controller == null)
		{
			for(int i = 0; i < boards.length; i++)
			if(boards[i] != null) boards[i].redstoneOut = false;
		}
		
		for(int s = 0; s < 6; s++)
		{
			canReceive[s] = false;
			
			if(controller != null)
			{
				CircuitBoard cb = getBoard(s);
				
				if(cb != null)
				{
					for(int i = 0; i < cb.items.length; i++)
					{
						if(cb.items[i] != null && cb.items[i].getItem() instanceof ItemModuleEnergyInput)
							canReceive[s] = ((ItemModuleEnergyInput)cb.items[i].getItem()).canReceive(cb.items[i], cb);
					}
				}
			}
		}
	}

	public boolean isOutputtingRS(int s)
	{ return isServer() && boards[s] != null && boards[s].redstoneOut; }

	public CircuitBoard getBoard(int side)
	{
		if(side >= 0 && side < boards.length)
			return boards[side]; return null;
	}
	
	public CircuitBoard getBoard(ForgeDirection dir)
	{
		if(dir == null || dir == ForgeDirection.UNKNOWN)
			return null;
		return getBoard(dir.ordinal());
	}
	
	public void onUpdate()
	{
		if(!isServer()) return;
		
		if(controller != null && tick % 20 == 0)
		{
			if(!controller.network.contains(this))
				onNetworkChanged(null);
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(hasCover)
		{
			if(is != null && is.getItem() instanceof IPaintable.IPainterItem)
				return false;
			
			return onBoardClicked(ep, is, side);
		}
		else
		{
			if(is != null && SilMat.coverBlock != null && InvUtils.itemsEquals(is, SilMat.coverBlock, false, true))
			{
				hasCover = true;
				
				if(!ep.capabilities.isCreativeMode)
					is.stackSize--;
				
				markDirty();
				return true;
			}
			else
			{
				MovingObjectPosition mop = MathHelperLM.rayTrace(ep);
				
				if(mop != null)
				{
					if(mop.subHit > 0)
					{
						int id1 = (mop.subHit - 1);
						
						if(MathHelperLM.getDir(id1) != ForgeDirection.UNKNOWN)
							return onBoardClicked(ep, is, id1);
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean onBoardClicked(EntityPlayer ep, ItemStack is, int id)
	{
		if(boards[id] == null)
		{
			if(is != null && is.getItem() == SilItems.i_circuit_board)
			{
				if(isServer())
				{
					boards[id] = new CircuitBoard(this, ForgeDirection.VALID_DIRECTIONS[id]);
					if(!ep.capabilities.isCreativeMode)
						is.stackSize--;
					markDirty();
				}
				
				return true;
			}
			else if(hasCover && is != null && ep.isSneaking() && LatCoreMC.isWrench(is))
			{
				if(!worldObj.isRemote)
				{
					hasCover = false;
					if(!ep.capabilities.isCreativeMode && SilMat.coverBlock != null)
						InvUtils.dropItem(ep, SilMat.coverBlock);
					markDirty();
				}
				
				return true;
			}
		}
		else
		{
			if(is != null && ep.isSneaking() && LatCoreMC.isWrench(is))
			{
				if(isServer())
				{
					if(!ep.capabilities.isCreativeMode && SilMat.coverBlock != null)
						InvUtils.dropItem(ep, new ItemStack(SilItems.i_circuit_board));
					
					for(int i = 0; i < boards[id].items.length; i++)
					{
						if(boards[id].items[i] != null && boards[id].items[i].stackSize > 0)
							InvUtils.dropItem(ep, boards[id].items[i]);
					}
					
					boards[id] = null;
					markDirty();
				}
				
				return true;
			}
			else
			{
				if(isServer())
					LatCoreMC.openGui(ep, this, 0);
				return true;
			}
		}
		
		return false;
	}
	
	public void onBroken()
	{
		super.onBroken();
		
		if(isServer())
		{
			if(hasCover && SilMat.coverBlock != null)
				InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, SilMat.coverBlock, 8);
			
			for(int i = 0; i < boards.length; i++)
			{
				if(boards[i] != null)
				{
					InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, new ItemStack(SilItems.i_circuit_board), 8);
					InvUtils.dropAllItems(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, boards[i].items);
					boards[i] = null;
				}
			}
		}
	}
	
	public boolean isAABBEnabled(int i)
	{
		if(i == 0) return true;
		
		//int id1 = (i - 1) / 2;
		
		ForgeDirection fd = MathHelperLM.getDir(i - 1);
		if(fd == ForgeDirection.UNKNOWN) return true;
		
		if(boards[fd.ordinal()] != null) return true;
		
		if(worldObj.getBlock(xCoord + fd.offsetX, yCoord + fd.offsetY, zCoord + fd.offsetZ) != Blocks.air) return true;
		
		EntityPlayer clientP = LC.proxy.getClientPlayer();
		
		if(clientP != null && clientP.getHeldItem() != null)
		{
			Item item = clientP.getHeldItem().getItem();
			
			if(item == SilItems.i_circuit_board || (SilMat.coverBlock != null && InvUtils.itemsEquals(clientP.getHeldItem(), SilMat.coverBlock, false, true))) return true;
		}
		
		return false;
	}
	
	public double getRelStoredEnergy()
	{ return (controller == null || controller.isInvalid()) ? 0D : (controller.storage.getEnergyStored() / (double)controller.storage.getMaxEnergyStored()); }
	
	public Container getContainer(EntityPlayer ep, int ID)
	{
		MovingObjectPosition mop = MathHelperLM.rayTrace(ep);
		
		if(mop != null)
		{
			CircuitBoard cb = null;
			
			if(hasCover) cb = getBoard(mop.sideHit);
			else if(mop.subHit > 0) cb = getBoard(mop.subHit - 1);
			
			if(cb != null)
			{
				if(ID == 0) return new ContainerCircuitBoard(ep, cb);
				else if(ID == 1) return new ContainerCircuitBoardSettings(ep, cb);
			}
		}
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int ID)
	{
		MovingObjectPosition mop = MathHelperLM.rayTrace(ep);
		
		if(mop != null)
		{
			CircuitBoard cb = null;
			
			if(hasCover) cb = getBoard(mop.sideHit);
			else if(mop.subHit > 0) cb = getBoard(mop.subHit - 1);
			
			if(cb != null)
			{
				if(ID == 0) return new GuiCircuitBoard(new ContainerCircuitBoard(ep, cb));
				else if(ID == 1) return new GuiCircuitBoardSettings(new ContainerCircuitBoardSettings(ep, cb));
			}
		}
		
		return null;
	}
	
	public void onClientAction(EntityPlayer ep, String action, NBTTagCompound data)
	{
		if(action.equals(ACTION_SET_CHANNEL))
		{
			int side = data.getByte("Side");
			int slot = data.getByte("Slot");
			int id = data.getByte("ID");
			byte ch = data.getByte("Channel");
			
			((ICBModule)boards[side].items[slot].getItem()).setChannel(boards[side].items[slot], boards[side], id, ch);
			markDirty();
		}
		else super.onClientAction(ep, action, data);
	}
	
	public void clientSetChannel(int side, int slot, int id, int ch)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setByte("Side", (byte)side);
		data.setByte("Slot", (byte)slot);
		data.setByte("ID", (byte)id);
		data.setByte("Channel", (byte)ch);
		sendClientAction(ACTION_SET_CHANNEL, data);
	}
	
	public boolean canConnectEnergy(ForgeDirection f)
	{ return canReceive[f.ordinal()]; }
	
	public int getEnergyStored(ForgeDirection f)
	{ return (controller == null) ? 0 : controller.getEnergyStored(f); }
	
	public int getMaxEnergyStored(ForgeDirection f)
	{ return (controller == null) ? 0 : controller.getMaxEnergyStored(f); }
	
	public int receiveEnergy(ForgeDirection f, int e, boolean b)
	{ return (controller == null || !canConnectEnergy(f)) ? 0 : controller.receiveEnergy(f, e, b); }
}