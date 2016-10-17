package com.latmod.silicio.api_impl.module;

import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISocketBlock;
import com.latmod.silicio.api_impl.SilCaps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public final class SocketBlock implements IItemHandler, ISocketBlock, INBTSerializable<NBTTagCompound>
{
    private final TileEntity tile;
    private final EnumFacing facing;
    private ItemStack stack;
    private IModuleContainer moduleContainer;

    public SocketBlock(TileEntity t, @Nullable EnumFacing f)
    {
        tile = t;
        facing = f;
    }

    public void updateModuleContainer()
    {
        moduleContainer = stack != null && stack.hasCapability(SilCaps.MODULE_CONTAINER, null) ? stack.getCapability(SilCaps.MODULE_CONTAINER, null) : null;

        if(moduleContainer != null && moduleContainer.getModule() == null)
        {
            moduleContainer = null;
        }
    }

    @Override
    public TileEntity getTile()
    {
        return tile;
    }

    @Override
    @Nullable
    public EnumFacing getFacing()
    {
        return facing;
    }

    @Override
    public boolean hasContainer()
    {
        return stack != null && moduleContainer != null;
    }

    @Override
    public ItemStack getStack()
    {
        return stack;
    }

    public void setItem(@Nullable ItemStack is)
    {
        stack = is;
        updateModuleContainer();
    }

    @Override
    public IModuleContainer getContainer()
    {
        return moduleContainer;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        if(facing != null)
        {
            nbt.setByte("Side", (byte) facing.ordinal());
        }

        if(stack != null)
        {
            NBTTagCompound itemTag = new NBTTagCompound();
            stack.writeToNBT(itemTag);
            nbt.setTag("Item", itemTag);
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        setItem(nbt.hasKey("Item") ? ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item")) : null);
    }

    @Override
    public int getSlots()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return stack;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack is, boolean simulate)
    {
        if(stack == null && is.hasCapability(SilCaps.MODULE_CONTAINER, null))
        {
            stack = LMInvUtils.singleCopy(is);
            updateModuleContainer();

            if(is.stackSize == 1)
            {
                return null;
            }

            ItemStack is0 = is.copy();
            is0.stackSize--;
            return is0;
        }

        return is;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return null;
    }
}