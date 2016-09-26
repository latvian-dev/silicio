package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileInvLM;
import com.latmod.silicio.api.module.IModuleContainer;
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
            if(progress > 0)
            {
                progress++;

                if(progress >= 100)
                {
                    ItemStack is0 = itemHandler.getStackInSlot(0);
                    ItemStack is1 = itemHandler.getStackInSlot(1);

                    if(is0 != null && is1 != null && is0.hasCapability(SilCaps.MODULE_CONTAINER, null) && is1.hasCapability(SilCaps.MODULE_CONTAINER, null))
                    {
                        IModuleContainer c0 = is0.getCapability(SilCaps.MODULE_CONTAINER, null);
                        IModuleContainer c1 = is1.getCapability(SilCaps.MODULE_CONTAINER, null);

                        if(c0.getModule() != null && c1.getModule() != null && c0.getModule().getID().equals(c1.getModule().getID()))
                        {
                            c1.getProperties().fromJson(c0.getProperties().getSerializableElement());
                            markDirty();
                        }
                    }

                    progress = 0;
                }
            }

            checkIfDirty();
        }
    }

    public void startCopying()
    {
        if(!worldObj.isRemote && progress == 0)
        {
            ItemStack is0 = itemHandler.getStackInSlot(0);
            ItemStack is1 = itemHandler.getStackInSlot(1);

            if(is0 != null && is1 != null && is0.hasCapability(SilCaps.MODULE_CONTAINER, null) && is1.hasCapability(SilCaps.MODULE_CONTAINER, null))
            {
                IModuleContainer c0 = is0.getCapability(SilCaps.MODULE_CONTAINER, null);
                IModuleContainer c1 = is1.getCapability(SilCaps.MODULE_CONTAINER, null);

                if(c0.getModule() != null && c1.getModule() != null && c0.getModule().getID().equals(c1.getModule().getID()))
                {
                    progress = 1;
                    markDirty();
                }
            }
        }
    }
}