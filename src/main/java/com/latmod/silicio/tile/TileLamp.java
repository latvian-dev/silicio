package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.SignalChannel;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.block.BlockLamp;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileLamp extends TileSilNet implements ITickable
{
    @Override
    public void writeTileClientData(@Nonnull NBTTagCompound tag)
    {
    }

    @Override
    public void readTileClientData(@Nonnull NBTTagCompound tag)
    {
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.SYNC;
    }

    @Override
    public void update()
    {
        if(!worldObj.isRemote && (worldObj.getTotalWorldTime() + pos.hashCode()) % 20L == 0L)
        {
            IBlockState state = getBlockState();
            worldObj.setBlockState(pos, state.withProperty(BlockLamp.ON, !state.getValue(BlockLamp.ON)));
        }
    }

    @Override
    public void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on)
    {
    }
}