package latmod.silicio.tile.cb;
import java.util.*;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.*;
import latmod.ftbu.core.gui.ContainerEmpty;
import latmod.ftbu.core.inv.LMInvUtils;
import latmod.ftbu.core.paint.*;
import latmod.ftbu.core.tile.*;
import latmod.ftbu.core.util.*;
import latmod.ftbu.core.waila.WailaDataAccessor;
import latmod.ftbu.mod.FTBU;
import latmod.latblocks.LatBlocksItems;
import latmod.silicio.SilItems;
import latmod.silicio.gui.*;
import latmod.silicio.gui.container.*;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.item.modules.config.ModuleConfigSegment;
import latmod.silicio.item.modules.io.ItemModuleEnergyInput;
import latmod.silicio.tile.cb.events.*;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.common.util.ForgeDirection;

// BlockCBCable //
public class TileCBCable extends TileBasicCBNetTile implements IPaintable, IGuiTile, IEnergyReceiver, IWailaTile.Body, ISecureTile
{
	public static final String ACTION_SET_CHANNEL = "silicio.channel";
	public static final String ACTION_MODULE_CONFIG = "silicio.mconfig";
	
	public final CircuitBoard[] boards = new CircuitBoard[6];
	public final Paint[] paint = new Paint[6];
	public boolean hasCover;
	private final boolean[] canReceive = new boolean[6];
	private final boolean[] isDisabled = new boolean[6];
	public final boolean[] renderCableSide = new boolean[6];
	public final boolean[] renderCover = new boolean[6];
	
	public TileCBCable() { }
	
	private void updateRenderSides()
	{
		for(int i = 0; i < 6; i++)
		{
			renderCableSide[i] = boards[i] != null || connectCable(this, i);
			renderCover[i] = true;
			
			TileEntity te = getTile(i);
			if(te != null && !te.isInvalid() && te instanceof TileCBCable)
				if(((TileCBCable)te).hasCover && ((TileCBCable)te).paint[Facing.oppositeSide[i]] != null)
					renderCover[i] = false;
		}
	}
	
	public void onNeighborBlockChange(Block b)
	{
		super.onNeighborBlockChange(b);
		updateRenderSides();
		if(isServer()) markDirty();
	}
	
	public void onLoaded()
	{
		super.onLoaded();
		updateRenderSides();
	}
	
	public void onUpdatePacket()
	{
		updateRenderSides();
	}
	
	public boolean rerenderBlock()
	{ return false; }
	
	public void onUpdateCB()
	{
		for(int s = 0; s < 6; s++)
		{
			canReceive[s] = false;
			if(getCBNetwork().hasWorkingController() && canReceiveEnergy(s))
				canReceive[s] = true;
		}
		
		for(int s = 0; s < boards.length; s++)
			if(boards[s] != null) boards[s].postUpdate();
	}
	
	public void onCBNetworkEvent(EventCB e)
	{
		if(e.is(EventCBNotified.class))
		{
			for(int i = 0; i < boards.length; i++)
				if(boards[i] != null) boards[i].redstoneOut = false;
			
			for(int s = 0; s < 6; s++)
				canReceive[s] = false;
			
			markDirty();
			onNeighborBlockChange(Blocks.air);
		}
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
			boards[id] = new CircuitBoard(this, id);
			boards[id].readTileData(tag1);
		}
		
		hasCover = tag.getBoolean("HasCover");
		Paint.readFromNBT(tag, "Paint", paint);

		Converter.toBools(isDisabled, IntList.asList(tag.getIntArray("Disabled")), true);
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
		
		IntList idx = Converter.fromBools(isDisabled, true);
		if(idx.size() > 0) tag.setIntArray("Disabled", idx.toArray());
	}
	
	public boolean setPaint(PaintData p)
	{
		if(p.paint != null && !isPaintValid(0, p.paint)) return false;
		
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
	
	public boolean isPaintValid(int side, Paint p)
	{ return p.block == Blocks.glass || p.block.renderAsNormalBlock(); }
	
	public static boolean connectCable(TileCBCable c, int s)
	{
		if(!c.isSideEnabled(s)) return false;
		TileEntity te = c.worldObj.getTileEntity(c.xCoord + Facing.offsetsXForSide[s], c.yCoord + Facing.offsetsYForSide[s], c.zCoord + Facing.offsetsZForSide[s]);
		return (te != null && te instanceof ICBNetTile && ((ICBNetTile)te).isSideEnabled(Facing.oppositeSide[s]));
	}
	
	private boolean canReceiveEnergy(int s)
	{
		if(boards[s] != null)
		{
			for(int i = 0; i < boards[s].items.length; i++)
			{
				if(boards[s].items[i] != null && boards[s].items[i].getItem() instanceof ItemModuleEnergyInput)
					return true;
			}
		}
		
		return false;
	}

	public boolean isOutputtingRS(int s)
	{ return boards[s] != null && boards[s].redstoneOut; }

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
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(is != null && is.getItem() instanceof IPainterItem)
			return false;
		
		MovingObjectPosition mop = MathHelperMC.rayTrace(ep);
		
		if(mop == null) return false;
		
		boolean isWrench = LMInvUtils.isWrench(is);
		
		int id = -1;
		
		if(!hasCover)
		{
			if(is != null && is.getItem() == LatBlocksItems.b_paintable.getItem())
			{
				if(isServer())
				{
					hasCover = true;
					
					if(!ep.capabilities.isCreativeMode)
						is.stackSize--;
					
					markDirty();
				}
				
				return true;
			}
			
			if(mop != null && mop.subHit >= 0 && mop.subHit <= 6)
			{
				id = (mop.subHit == 6) ? mop.sideHit : mop.subHit;
				
				if(isServer() && mop.subHit == 6 && ep.isSneaking() && isWrench)
				{
					if(worldObj.setBlockToAir(xCoord, yCoord, zCoord))
						LMInvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, new ItemStack(SilItems.b_cbcable), 8);
				}
			}
		}
		else id = side;
		
		if(id < 0 || id >= 6) return true;
		
		if(is != null && boards[id] == null && is.getItem() == SilItems.b_cbcable.getItem()) return false;
		
		if(isWrench && !ep.isSneaking())
		{
			if(boards[id] != null) return true;
			setDisabled(id, !isDisabled[id]);
			return true;
		}
		
		if(!isServer()) return true;
		
		if(boards[id] == null)
		{
			if(is != null && is.getItem() == SilItems.i_circuit_board)
			{
				setDisabled(id, false);
				boards[id] = new CircuitBoard(this, id);
				if(!ep.capabilities.isCreativeMode) is.stackSize--;
				markDirty();
			}
			else if(isWrench)
			{
				if(!ep.isSneaking())
				{
					isDisabled[id] = !isDisabled[id];
					markDirty();
				}
				else if(hasCover)
				{
					hasCover = false;
					if(!ep.capabilities.isCreativeMode)
						LMInvUtils.dropItem(ep, new ItemStack(LatBlocksItems.b_paintable));
					markDirty();
				}
			}
		}
		else
		{
			if(is != null && ep.isSneaking() && isWrench)
			{
				if(!ep.capabilities.isCreativeMode)
					LMInvUtils.dropItem(ep, new ItemStack(SilItems.i_circuit_board));
				
				for(int i = 0; i < boards[id].items.length; i++)
				{
					if(boards[id].items[i] != null && boards[id].items[i].stackSize > 0)
						LMInvUtils.dropItem(ep, boards[id].items[i]);
				}
				
				boards[id] = null;
				markDirty();
			}
			else
			{
				LatCoreMC.openGui(ep, this, guiData(id, 0, -1));
			}
		}
		
		return true;
	}
	
	public void onBroken()
	{
		super.onBroken();
		
		if(isServer())
		{
			if(hasCover) LMInvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, new ItemStack(LatBlocksItems.b_paintable), 8);
			
			for(int i = 0; i < boards.length; i++)
			{
				if(boards[i] != null)
				{
					LMInvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, new ItemStack(SilItems.i_circuit_board), 8);
					LMInvUtils.dropAllItems(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, boards[i].items);
					boards[i] = null;
				}
			}
		}
	}
	
	public boolean isAABBEnabled(int i)
	{
		if(i == 6 || boards[i] != null) return true;
		
		TileEntity te = worldObj.getTileEntity(xCoord + Facing.offsetsXForSide[i], yCoord + Facing.offsetsYForSide[i], zCoord + Facing.offsetsZForSide[i]);
		if(te instanceof ICBNetTile) return true;
		
		EntityPlayer clientP = FTBU.proxy.getClientPlayer();
		
		if(clientP != null && clientP.getHeldItem() != null)
		{
			Item item = clientP.getHeldItem().getItem();
			if((item == SilItems.i_circuit_board && !worldObj.isAirBlock(xCoord + Facing.offsetsXForSide[i], yCoord + Facing.offsetsYForSide[i], zCoord + Facing.offsetsZForSide[i]))) return true;
		}
		
		return false;
	}
	
	public double getRelStoredEnergy()
	{ return getCBNetwork().hasWorkingController() ? (getCBNetwork().controller.storage.getEnergyStored() / (double)getCBNetwork().controller.storage.getMaxEnergyStored()) : 0; }
	
	public static NBTTagCompound guiData(int side, int gui, int module)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setByte("Side", (byte)side);
		data.setByte("Gui", (byte)gui);
		if(module > 0) data.setByte("MID", (byte)module);
		return data;
	}
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{
		int side = data.getByte("Side");
		int gui = data.getByte("Gui");
		
		CircuitBoard t = getBoard(side);
		
		if(t != null)
		{
			if(gui == 0) return new ContainerCircuitBoard(ep, t);
			else if(gui == 1) return new ContainerCircuitBoardSettings(ep, t);
			else if(gui == 2) return new ContainerModuleSettings(ep, t);
			else if(gui == 3) return new ContainerEmpty(ep, t);
		}
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{
		int side = data.getByte("Side");
		int gui = data.getByte("Gui");
		int moduleID = data.getByte("MID");
		
		CircuitBoard cb = getBoard(side);
		
		if(cb != null)
		{
			if(gui == 0) return new GuiCircuitBoard(new ContainerCircuitBoard(ep, cb));
			else if(gui == 1) return new GuiCircuitBoardSettings(new ContainerCircuitBoardSettings(ep, cb));
			else if(gui == 2) return new GuiModuleSettings(new ContainerModuleSettings(ep, cb), moduleID);
			else if(gui == 3) return new GuiSelectChannels(new ContainerEmpty(ep, cb), moduleID);
		}
		
		return null;
	}
	
	public void onClientAction(EntityPlayerMP ep, String action, NBTTagCompound data)
	{
		if(action.equals(ACTION_SET_CHANNEL))
		{
			int side = data.getByte("F");
			int moduleID = data.getByte("M");
			int id = data.getByte("I");
			int col = data.getInteger("C");
			
			ItemModule.setChannel(boards[side].items[moduleID], id, col);
			markDirty();
		}
		else if(action.equals(ACTION_MODULE_CONFIG))
		{
			int side = data.getByte("F");
			int moduleID = data.getByte("M");
			int id = data.getByte("I");
			NBTTagCompound tag = (NBTTagCompound)data.getTag("D");
			
			for(ModuleConfigSegment mcs : ((ItemModule)boards[side].items[moduleID].getItem()).moduleConfig)
			{
				if(mcs.ID == id)
				{
					mcs.onConfigReceived(boards[side], moduleID, tag);
					return;
				}
			}
		}
		else super.onClientAction(ep, action, data);
	}
	
	public void clientSetChannel(int side, int moduleID, int id, int ch)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setByte("F", (byte)side);
		data.setByte("M", (byte)moduleID);
		data.setByte("I", (byte)id);
		data.setInteger("C", ch);
		sendClientAction(ACTION_SET_CHANNEL, data);
	}
	
	public void clientModuleConfig(CircuitBoard cb, int moduleID, int c, NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setByte("F", (byte)cb.side);
		data.setByte("M", (byte)moduleID);
		data.setByte("I", (byte)c);
		if(tag != null) data.setTag("D", tag);
		sendClientAction(ACTION_MODULE_CONFIG, data);
	}
	
	public boolean canConnectEnergy(ForgeDirection f)
	{ return canReceive[f.ordinal()]; }
	
	public int getEnergyStored(ForgeDirection f)
	{ return getCBNetwork().hasWorkingController() ? getCBNetwork().controller.getEnergyStored(f) : 0; }
	
	public int getMaxEnergyStored(ForgeDirection f)
	{ return getCBNetwork().hasWorkingController() ? getCBNetwork().controller.getMaxEnergyStored(f) : 0; }
	
	public int receiveEnergy(ForgeDirection f, int e, boolean b)
	{ return (getCBNetwork().hasWorkingController() && canConnectEnergy(f)) ? getCBNetwork().controller.receiveEnergy(f, e, b) : 0; }
	
	public void setDisabled(int side, boolean b)
	{
		if(isDisabled[side] != b)
		{
			isDisabled[side] = b;
			notifyNeighbors();
			onNeighborBlockChange(getBlockType());
			markDirty();
			
			TileEntity te = worldObj.getTileEntity(xCoord + Facing.offsetsXForSide[side], yCoord + Facing.offsetsYForSide[side], zCoord + Facing.offsetsZForSide[side]);
			if(te != null && !te.isInvalid() && te instanceof TileCBCable)
			{
				TileCBCable t = (TileCBCable)te;
				if(t.isDisabled[Facing.oppositeSide[side]] != b)
				{
					t.isDisabled[Facing.oppositeSide[side]] = b;
					t.notifyNeighbors();
					t.onNeighborBlockChange(getBlockType());
					t.markDirty();
				}
			}
		}
	}
	
	public boolean isSideEnabled(int side)
	{
		if(side < 0 || side >= 6) return false;
		return !isDisabled[side];
	}
	
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{
		int i = data.position.subHit;
		if(i >= 0 && i < 6) { if(!isSideEnabled(i)) info.add("Disabled"); }
	}
	
	public LMSecurity getSecurity()
	{ return getCBNetwork().hasWorkingController() ? getCBNetwork().controller.getSecurity() : security; }
	
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return getSecurity().canInteract(ep); }
	
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }

	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    { return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1D, yCoord + 1D, zCoord + 1D); }
}