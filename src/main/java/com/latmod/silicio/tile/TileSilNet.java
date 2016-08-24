package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import com.latmod.lib.util.LMStringUtils;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api.ISilNetTile;
import com.latmod.silicio.api.SilCapabilities;
import com.latmod.silicio.api.SilNet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public abstract class TileSilNet extends TileLM implements ISilNetTile
{
    protected UUID controllerID;

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
    public void writeTileData(@Nonnull NBTTagCompound nbt)
    {
        if(controllerID != null)
        {
            nbt.setString("ControllerID", LMStringUtils.fromUUID(controllerID));
        }
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound nbt)
    {
        controllerID = nbt.hasKey("ControllerID") ? LMStringUtils.fromString(nbt.getString("ControllerID")) : null;
    }

    @Override
    public void writeTileClientData(@Nonnull NBTTagCompound nbt)
    {
        if(controllerID != null)
        {
            nbt.setString("CID", LMStringUtils.fromUUID(controllerID));
        }
    }

    @Override
    public void readTileClientData(@Nonnull NBTTagCompound nbt)
    {
        controllerID = nbt.hasKey("CID") ? LMStringUtils.fromString(nbt.getString("CID")) : null;
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
    @Nullable
    public UUID getControllerID()
    {
        return controllerID;
    }

    @Override
    public void setControllerID(@Nullable UUID id, EntityPlayer playerIn)
    {
        if(!playerIn.worldObj.isRemote)
        {
            ISilNetController c = SilNet.findController(controllerID);

            controllerID = id;

            if(c != null)
            {
                c.onSilNetUpdate();
            }

            c = SilNet.findController(controllerID);

            if(c != null)
            {
                c.onSilNetUpdate();
            }
        }
    }

    @Override
    public void provideSignals(@Nonnull ISilNetController c)
    {
    }

    @Override
    public void onSignalChanged(@Nonnull ISilNetController c, int channel, boolean on)
    {
    }
}