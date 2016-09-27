package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.lib.tile.TileLM;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileESU extends TileLM implements ITickable
{
    public TileESU()
    {
    }

    @Override
    public void update()
    {
        checkIfDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return super.getCapability(capability, facing);
    }
}