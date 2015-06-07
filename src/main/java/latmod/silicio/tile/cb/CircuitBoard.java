package latmod.silicio.tile.cb;
import latmod.ftbu.core.InvUtils;
import latmod.silicio.item.modules.ItemModule;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.common.util.ForgeDirection;

public class CircuitBoard implements IInventory
{
	public final TileCBCable cable;
	public final int side;
	public final int sideOpposite;
	public final ForgeDirection sideF;
	public final ForgeDirection sideOppositeF;
	public final ChunkCoordinates sidePos;
	
	public ItemStack items[] = new ItemStack[12];
	public boolean dropItems = true;
	public boolean redstoneOut = false;
	private Boolean prevRedstoneOut = null;
	
	public CircuitBoard(TileCBCable t, int f)
	{
		cable = t;
		side = f;
		sideOpposite = Facing.oppositeSide[side];
		sideF = ForgeDirection.VALID_DIRECTIONS[side];
		sideOppositeF = sideF.getOpposite();
		sidePos = new ChunkCoordinates(cable.xCoord + Facing.offsetsXForSide[side], cable.yCoord + Facing.offsetsYForSide[side], cable.zCoord + Facing.offsetsZForSide[side]);
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		InvUtils.readItemsFromNBT(items, tag, "Items");
		redstoneOut = tag.getBoolean("RSOut");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		if(items != null) InvUtils.writeItemsToNBT(items, tag, "Items");
		tag.setBoolean("RSOut", redstoneOut);
	}
	
	public ItemModule getModule(int moduleID)
	{
		if(moduleID < 0 || moduleID >= items.length || items[moduleID] == null) return null;
		if(items[moduleID].getItem() instanceof ItemModule) return (ItemModule)items[moduleID].getItem();
		return null;
	}
	
	public void postUpdate()
	{
		if(prevRedstoneOut == null || prevRedstoneOut != redstoneOut)
		{
			prevRedstoneOut = redstoneOut;
			cable.markDirty();
			cable.getWorldObj().notifyBlocksOfNeighborChange(cable.xCoord, cable.yCoord, cable.zCoord, cable.blockType);
		}
	}
	
	public TileEntity getFacingTile()
	{
		TileEntity te = cable.getWorldObj().getTileEntity(sidePos.posX, sidePos.posY, sidePos.posZ);
		return (te == null || te.isInvalid()) ? null : te;
	}
	
	public Block getFacingBlock()
	{ return cable.getWorldObj().getBlock(sidePos.posX, sidePos.posY, sidePos.posZ); }
	
	public int getSizeInventory()
	{ return items.length; }
	
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }
	
	public ItemStack decrStackSize(int slot, int amt)
	{ return InvUtils.decrStackSize(this, slot, amt); }
	
	public ItemStack getStackInSlotOnClosing(int i)
	{ return InvUtils.getStackInSlotOnClosing(this, i); }
	
	public void setInventorySlotContents(int i, ItemStack is)
	{ items[i] = is; }
	
	public String getInventoryName()
	{ return "Circuit Board"; }
	
	public boolean hasCustomInventoryName()
	{ return true; }
	
	public int getInventoryStackLimit()
	{ return 1; }
	
	public void markDirty()
	{ cable.markDirty(); }
	
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return false; }
	
	public void openInventory() { }
	public void closeInventory() { }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
}