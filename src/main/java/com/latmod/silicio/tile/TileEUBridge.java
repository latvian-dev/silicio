package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import com.latmod.silicio.api.SilCapabilities;
import com.latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 01.05.2016.
 */
public class TileEUBridge extends TileLM
{
    public SilEnergyTank energyTank;

    public TileEUBridge()
    {
        energyTank = new SilEnergyTank(1000D);
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound tag)
    {
        tag.setDouble("SilEnergy", energyTank.getEnergy());
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound tag)
    {
        energyTank.setEnergy(tag.getDouble("SilEnergy"));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing)
    {
        if(capability == SilCapabilities.ENERGY_TANK)
        {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing)
    {
        if(capability == SilCapabilities.ENERGY_TANK)
        {
            return (T) energyTank;
        }

        return super.getCapability(capability, facing);
    }
}