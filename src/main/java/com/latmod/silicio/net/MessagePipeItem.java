package com.latmod.silicio.net;

import com.feed_the_beast.ftbl.lib.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.lib.net.MessageToClient;
import com.feed_the_beast.ftbl.lib.util.LMNetUtils;
import com.latmod.silicio.api.pipes.TransportedItem;
import com.latmod.silicio.api_impl.SilCaps;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public class MessagePipeItem extends MessageToClient<MessagePipeItem>
{
    private BlockPos pos;
    private TransportedItem item;

    public MessagePipeItem()
    {
    }

    public MessagePipeItem(BlockPos p, TransportedItem i)
    {
        pos = p;
        item = i;
    }

    @Override
    public LMNetworkWrapper getWrapper()
    {
        return SilicioNet.NET;
    }

    @Override
    public void fromBytes(ByteBuf io)
    {
        pos = LMNetUtils.readPos(io);
        item = new TransportedItem();
        item.fromBytes(io);
    }

    @Override
    public void toBytes(ByteBuf io)
    {
        LMNetUtils.writePos(io, pos);
        item.toBytes(io);
    }

    @Override
    public void onMessage(MessagePipeItem m, EntityPlayer player)
    {
        TileEntity te = player.worldObj.getTileEntity(m.pos);

        EnumFacing facing = EnumFacing.VALUES[m.item.src];
        if(te != null && te.hasCapability(SilCaps.PIPE, facing))
        {
            te.getCapability(SilCaps.PIPE, facing).insertItemInPipe(m.item, false);
        }
    }
}