package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileESU extends TileLM implements ITickable
{
    public TileESU()
    {
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound tag)
    {
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound tag)
    {
    }

    @Override
    public void update()
    {
        checkIfDirty();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing)
    {
        return super.hasCapability(capability, facing);
    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing)
    {
        return super.getCapability(capability, facing);
    }
}