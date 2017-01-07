package com.latmod.silicio.tile.pipes;

import com.latmod.silicio.api.pipes.TransportedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public class TileExtractionPipe extends TilePipeBase
{
    @Override
    public int updatePipe()
    {
        if(worldObj.isRemote || !worldObj.isBlockPowered(pos))
        {
            return 20;
        }

        for(EnumFacing facing : EnumFacing.VALUES)
        {
            TileEntity te = worldObj.getTileEntity(pos.offset(facing));
            EnumFacing opp = facing.getOpposite();

            if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, opp))
            {
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, opp);

                for(int j = 0; j < handler.getSlots(); j++)
                {
                    ItemStack stack = handler.extractItem(j, 1, false);

                    if(stack != null && stack.stackSize > 0)
                    {
                        TransportedItem item = new TransportedItem();
                        item.stack = stack;
                        item.setSpeed(1F / 20F);
                        item.dst = (byte) opp.ordinal();
                        item.filters = (short) 0;
                        insertItemInPipe(item, false);
                        break;
                    }
                }
            }
        }

        return 20;
    }
}