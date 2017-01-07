package com.latmod.silicio.tile.pipes;

import com.feed_the_beast.ftbl.lib.tile.TileLM;
import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.silicio.api.pipes.IPipe;
import com.latmod.silicio.api.pipes.IPipeConnection;
import com.latmod.silicio.api.pipes.TransportedItem;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.net.MessagePipeItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public class TilePipeBase extends TileLM implements ITickable, IPipe, IPipeConnection
{
    private static final byte[] TEMP_ARRAY = new byte[6];
    public static final float[] TEMP_POS = new float[3];
    public static final byte[] FACING_BIT = new byte[6];
    public static final byte[] OPPOSITE = new byte[6];

    static
    {
        for(byte i = 0; i < 6; i++)
        {
            FACING_BIT[i] = (byte) (1 << i);
            OPPOSITE[i] = (byte) EnumFacing.VALUES[i].getOpposite().ordinal();
        }
    }

    public final List<TransportedItem> transportedItems;
    public byte connectedPipes = -1;
    public int cooldown;

    public TilePipeBase()
    {
        transportedItems = new ArrayList<>();
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        NBTTagList list = new NBTTagList();

        for(TransportedItem item : transportedItems)
        {
            list.appendTag(item.serializeNBT());
        }

        nbt.setTag("Items", list);
        nbt.setShort("Cooldown", (short) cooldown);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        transportedItems.clear();
        NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++)
        {
            TransportedItem item = new TransportedItem();
            item.deserializeNBT(list.getCompoundTagAt(i));
            transportedItems.add(item);
        }

        cooldown = nbt.getShort("Cooldown");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == SilCaps.PIPE_CONNECTION || capability == SilCaps.PIPE || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return (capability == SilCaps.PIPE_CONNECTION || capability == SilCaps.PIPE) ? (T) this : super.getCapability(capability, facing);
    }

    @Override
    public void update()
    {
        if(!transportedItems.isEmpty())
        {
            for(int i = transportedItems.size() - 1; i >= 0; i--)
            {
                TransportedItem item = transportedItems.get(i);
                modifyItem(item);

                item.pprogress = item.progress;
                item.progress += item.speed;

                if(item.progress >= 1F)
                {
                    item.progress = item.pprogress = 0F;
                    transportedItems.remove(i);

                    if(!worldObj.isRemote)
                    {
                        ItemStack stack = pushItem(item, pos.offset(EnumFacing.VALUES[item.dst]));

                        if(stack != null && stack.stackSize > 0)
                        {
                            onItemRejected(stack, item);
                        }
                    }
                }
            }
        }

        if(connectedPipes == -1)
        {
            updatePipeConnections();
        }

        cooldown--;
        if(cooldown <= 0)
        {
            cooldown = updatePipe();
        }
    }

    public int updatePipe()
    {
        return 20;
    }

    @Nullable
    private ItemStack pushItem(TransportedItem item, BlockPos pos1)
    {
        TileEntity te = worldObj.getTileEntity(pos1);

        if(te == null)
        {
            return null;
        }

        EnumFacing opp = EnumFacing.VALUES[item.dst].getOpposite();

        if(te.hasCapability(SilCaps.PIPE, opp))
        {
            ItemStack itemStack = te.getCapability(SilCaps.PIPE, opp).insertItemInPipe(item, false);

            if(itemStack != null && itemStack.stackSize > 0)
            {
                return itemStack;
            }
        }
        else if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, opp))
        {
            IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, opp);
            ItemStack stack = item.stack.copy();

            for(int j = 0; j < itemHandler.getSlots(); j++)
            {
                stack = itemHandler.insertItem(j, stack, worldObj.isRemote);

                if(stack == null)
                {
                    return null;
                }
            }

            return stack;
        }

        return null;
    }

    private void onItemRejected(ItemStack itemStack, TransportedItem item)
    {
        LMInvUtils.dropItem(worldObj, pos, itemStack, 5);
    }

    protected byte getItemDirection(TransportedItem item)
    {
        updatePipeConnections();
        byte size = 0;

        for(byte i = 0; i < 6; i++)
        {
            if(i != item.src && (connectedPipes & FACING_BIT[i]) != 0 && canPipeConnect(item))
            {
                TEMP_ARRAY[size] = i;
                size++;
            }
        }

        return size == 0 ? -1 : TEMP_ARRAY[worldObj.rand.nextInt(size)];
    }

    protected void modifyItem(TransportedItem item)
    {
    }

    @Override
    public ItemStack insertItemInPipe(TransportedItem item, boolean simulate)
    {
        if(item.stack == null || item.stack.stackSize <= 0)
        {
            return null;
        }

        if(worldObj.isRemote)
        {
            transportedItems.add(item);
            return null;
        }

        item.src = item.dst == -1 ? -1 : OPPOSITE[item.dst];
        item.dst = getItemDirection(item);

        if(item.dst == -1 || item.src == -1)
        {
            return item.stack;
        }

        transportedItems.add(item);
        item.progress %= 1F;
        new MessagePipeItem(pos, item).sendToAllAround(worldObj.provider.getDimension(), pos, 64);
        return null;
    }

    @Override
    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        connectedPipes = -1;
        updatePipeConnections();
    }

    public void updatePipeConnections()
    {
        if(worldObj == null)
        {
            return;
        }

        connectedPipes = 0;

        for(byte i = 0; i < 6; i++)
        {
            BlockPos pos1 = pos.offset(EnumFacing.VALUES[i]);
            IBlockState state = worldObj.getBlockState(pos1);

            if(state.getBlock().hasTileEntity(state))
            {
                TileEntity te = worldObj.getTileEntity(pos1);

                if(te != null && connectPipe(te, i, null))
                {
                    connectedPipes |= FACING_BIT[i];
                }
            }
        }
    }

    protected boolean connectPipe(TileEntity te, byte facing, @Nullable TransportedItem item)
    {
        EnumFacing opp = EnumFacing.VALUES[facing].getOpposite();

        if(te.hasCapability(SilCaps.PIPE_CONNECTION, opp))
        {
            return te.getCapability(SilCaps.PIPE_CONNECTION, opp).canPipeConnect(item);
        }

        if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, opp))
        {
            if(item != null)
            {
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, opp);
                ItemStack stack = item.stack.copy();

                for(int i = 0; i < handler.getSlots(); i++)
                {
                    stack = handler.insertItem(i, stack, true);

                    if(stack == null || stack.stackSize <= 0)
                    {
                        return true;
                    }
                }

                return false;
            }

            return true;
        }

        return false;
    }

    public boolean isPipeConnected(EnumFacing facing)
    {
        return (connectedPipes & FACING_BIT[facing.ordinal()]) != 0;
    }

    public boolean onRightClick(EntityPlayer player, @Nullable EnumFacing part, EnumFacing facing)
    {
        return false;
    }

    public void onBroken()
    {
        for(TransportedItem item : transportedItems)
        {
            LMInvUtils.dropItem(worldObj, pos, item.stack, 10);
        }

        transportedItems.clear();
    }

    @Override
    public boolean canPipeConnect(@Nullable TransportedItem item)
    {
        return true;
    }
}