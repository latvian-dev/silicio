package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileInvLM;
import com.latmod.silicio.api.SilicioAPI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class TileModuleIO extends TileInvLM
{
    public TileModuleIO()
    {
        super(2);
    }

    @Override
    protected ItemStackHandler createHandler(int size)
    {
        return new ItemStackHandler(size)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                TileModuleIO.this.markDirty();
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
            {
                if(stack == null || stack.stackSize == 0)
                {
                    return null;
                }
                else if(!stack.hasCapability(SilicioAPI.MODULE_CONTAINER, null))
                {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }
}