package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileInvLM;
import com.latmod.silicio.api_impl.SilCaps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class TileModuleIO extends TileInvLM implements ITickable
{
    public byte progress;
    public short energy;

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
                else if(!stack.hasCapability(SilCaps.MODULE_CONTAINER, null))
                {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        nbt.setByte("Progress", progress);
        nbt.setShort("Energy", energy);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        progress = nbt.getByte("Progress");
        energy = nbt.getShort("Energy");
    }

    @Override
    public void update()
    {
        if(!worldObj.isRemote)
        {
            progress++;
            energy++;

            if(progress >= 100)
            {
                progress = 0;
            }

            if(energy >= 3200)
            {
                energy = 0;
            }

            checkIfDirty();
        }
    }

    public void startCopying()
    {
    }
}