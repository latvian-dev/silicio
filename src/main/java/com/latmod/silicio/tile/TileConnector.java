package com.latmod.silicio.tile;

import com.latmod.silicio.api.tile.ISilNetConnector;
import net.minecraft.block.BlockDirectional;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class TileConnector extends TileSilNet implements ISilNetConnector
{
    private String name = "";
    private UUID connectorID;

    public void setName(String s)
    {
        name = s;
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        nbt.setString("Name", name);
        nbt.setUniqueId("ConnectorID", getConnectorID());
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        name = nbt.getString("Name");
        connectorID = nbt.hasUniqueId("ConnectorID") ? nbt.getUniqueId("ConnectorID") : null;
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);

        if(!name.isEmpty())
        {
            nbt.setString("N", name);
        }

        nbt.setLong("CNIDM", getConnectorID().getMostSignificantBits());
        nbt.setLong("CNIDL", getConnectorID().getLeastSignificantBits());
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);
        name = nbt.getString("N");
        connectorID = nbt.hasKey("CNIDM") ? new UUID(nbt.getLong("CNIDM"), nbt.getLong("CNIDL")) : null;
    }

    @Override
    public UUID getConnectorID()
    {
        if(connectorID == null)
        {
            connectorID = UUID.randomUUID();
        }

        return connectorID;
    }

    @Override
    public String getConnectorName()
    {
        return name.isEmpty() ? getPos().toString() : name;
    }

    @Nullable
    @Override
    public TileEntity getConnectedTile()
    {
        return isInvalid() ? null : worldObj.getTileEntity(getPos().offset(getBlockState().getValue(BlockDirectional.FACING)));
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentString(getConnectorName());
    }
}