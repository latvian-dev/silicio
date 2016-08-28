package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import com.latmod.silicio.api.SilicioAPI;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISilNetTile;
import gnu.trove.map.TIntByteMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

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
        SilicioAPI.get().addSilNetTile(this);
    }

    @Override
    public void invalidate()
    {
        SilicioAPI.get().removeSilNetTile(this);
        super.invalidate();
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);

        if(controllerID != null)
        {
            nbt.setUniqueId("ControllerID", controllerID);
        }
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        controllerID = nbt.hasUniqueId("ControllerID") ? nbt.getUniqueId("ControllerID") : null;
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);

        if(controllerID != null)
        {
            nbt.setLong("CIDM", controllerID.getMostSignificantBits());
            nbt.setLong("CIDL", controllerID.getLeastSignificantBits());
        }
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);
        controllerID = nbt.hasKey("CIDM") ? new UUID(nbt.getLong("CIDM"), nbt.getLong("CIDL")) : null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == SilicioAPI.SILNET_TILE || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == SilicioAPI.SILNET_TILE)
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
            ISilNetController c = SilicioAPI.get().findSilNetController(controllerID);
            controllerID = id;

            if(c != null)
            {
                c.onSilNetUpdate();
            }

            c = SilicioAPI.get().findSilNetController(controllerID);

            if(c != null)
            {
                c.onSilNetUpdate();
            }
        }
    }

    @Override
    public void provideSignals(ISilNetController controller)
    {
    }

    @Override
    public void onSignalsChanged(ISilNetController controller, TIntByteMap channels)
    {
    }
}