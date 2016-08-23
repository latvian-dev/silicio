package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import com.latmod.silicio.api.ISignalBus;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api.ISilNetTile;
import com.latmod.silicio.api.SilCapabilities;
import com.latmod.silicio.api.SilNet;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public abstract class TileSilNet extends TileLM implements ISilNetTile
{
    @Override
    public void onLoad()
    {
        super.onLoad();
        SilNet.add(this);
    }

    @Override
    public void invalidate()
    {
        SilNet.remove(this);
        super.invalidate();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing)
    {
        return capability == SilCapabilities.SILNET_TILE || super.hasCapability(capability, facing);

    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing)
    {
        if(capability == SilCapabilities.SILNET_TILE)
        {
            return (T) this;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void provideSignals(ISilNetController c, ISignalBus channels)
    {
    }

    @Override
    public void onSignalChanged(ISilNetController c, int channel, boolean on)
    {
    }
}