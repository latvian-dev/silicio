package com.latmod.silicio.tile;

import com.latmod.silicio.api.tile.ISilNetConnector;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISilNetTile;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.api_impl.SilicioAPI_Impl;
import gnu.trove.impl.Constants;
import gnu.trove.map.hash.TShortByteHashMap;
import gnu.trove.set.hash.TShortHashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileSilNetController extends TileSilNet implements ITickable, ISilNetController, IEnergyStorage
{
    private int energyStored;
    private final TShortHashSet signals = new TShortHashSet();
    private final TShortHashSet signalsPrev = new TShortHashSet();
    private final TShortByteHashMap changedSignals = new TShortByteHashMap(3, Constants.DEFAULT_LOAD_FACTOR, (short) 0, (byte) -1);
    private Collection<TileEntity> network = new ArrayList<>();
    private Map<UUID, ISilNetConnector> connectedTiles = new HashMap<>();
    private boolean updateNetwork = true;
    private NBTTagCompound variables = new NBTTagCompound();

    @Override
    protected boolean rerenderBlock()
    {
        return true;
    }

    @Override
    @Nullable
    public UUID getControllerID()
    {
        if(controllerID == null)
        {
            controllerID = UUID.randomUUID();
        }

        return controllerID;
    }

    @Override
    public void setControllerID(@Nullable UUID id, EntityPlayer playerIn)
    {
    }

    private void readSet(TShortHashSet set, NBTTagCompound nbt, String id)
    {
        set.clear();

        for(int i : nbt.getIntArray(id))
        {
            set.add((short) i);
        }
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        readSet(signals, nbt, "Signals");
        readSet(signalsPrev, nbt, "PrevSignals");
        variables = nbt.getCompoundTag("Variables");
        energyStored = nbt.getInteger("Energy");
    }

    private void writeSet(TShortHashSet set, NBTTagCompound nbt, String id)
    {
        int[] ai = new int[set.size()];
        int idx = -1;

        for(short s : set.toArray())
        {
            ai[++idx] = s;
        }

        nbt.setIntArray(id, ai);
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        writeSet(signals, nbt, "Signals");
        writeSet(signalsPrev, nbt, "PrevSignals");
        nbt.setTag("Variables", variables);
        nbt.setInteger("Energy", energyStored);
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);
        signalsPrev.clear();
        readSet(signals, nbt, "S");
        variables = nbt.getCompoundTag("V");
        energyStored = nbt.getInteger("E");
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);
        writeSet(signals, nbt, "S");
        nbt.setTag("V", variables);
        nbt.setInteger("E", energyStored);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityEnergy.ENERGY)
        {
            return (T) this;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void update()
    {
        if(getSide().isClient())
        {
            return;
        }

        getNetwork();

        if(!network.isEmpty())
        {
            signalsPrev.clear();
            signalsPrev.addAll(signals);

            for(TileEntity tile : network)
            {
                if(tile.hasCapability(SilCaps.SILNET_TILE, null))
                {
                    tile.getCapability(SilCaps.SILNET_TILE, null).provideSignals(this);
                }
            }

            if(!signalsPrev.isEmpty())
            {
                signalsPrev.forEach(id ->
                {
                    if(!signals.contains(id))
                    {
                        changedSignals.put(id, (byte) 0);
                    }

                    return true;
                });
            }

            if(!changedSignals.isEmpty())
            {
                for(TileEntity tile : network)
                {
                    if(tile.hasCapability(SilCaps.SILNET_TILE, null))
                    {
                        tile.getCapability(SilCaps.SILNET_TILE, null).onSignalsChanged(this, changedSignals);
                    }
                }
            }

            signals.clear();
            changedSignals.clear();
        }

        checkIfDirty();
    }

    @Override
    public void onSilNetUpdate()
    {
        updateNetwork = true;
    }

    @Override
    public Collection<TileEntity> getNetwork()
    {
        if(updateNetwork)
        {
            network.clear();
            SilicioAPI_Impl.INSTANCE.findSilNetTiles(network, getControllerID());
            network.remove(this);

            connectedTiles.clear();

            for(TileEntity tile : network)
            {
                ISilNetTile tile1 = tile.getCapability(SilCaps.SILNET_TILE, null);

                if(tile1 instanceof ISilNetConnector)
                {
                    connectedTiles.put(((ISilNetConnector) tile1).getConnectorID(), (ISilNetConnector) tile1);
                }
            }

            updateNetwork = false;
        }

        return network;
    }

    @Override
    public Map<UUID, ISilNetConnector> getConnectors()
    {
        getNetwork();
        return connectedTiles;
    }

    @Override
    public boolean getSignal(short id)
    {
        return id != 0 && signals.contains(id);
    }

    @Override
    public void provideSignal(short id)
    {
        if(id != 0)
        {
            signals.add(id);

            if(!signalsPrev.contains(id))
            {
                changedSignals.put(id, (byte) 1);
            }
        }
    }

    @Override
    public NBTTagCompound getVariables()
    {
        return variables;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int energyReceived = Math.min(getMaxEnergyStored() - energyStored, Math.min(2500, maxReceive));

        if(!simulate)
        {
            energyStored += energyReceived;
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int energyExtracted = Math.min(energyStored, Math.min(2500, maxExtract));

        if(!simulate)
        {
            energyStored -= energyExtracted;
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored()
    {
        return energyStored;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return 500000;
    }

    @Override
    public boolean canExtract()
    {
        return true;
    }

    @Override
    public boolean canReceive()
    {
        return true;
    }
}