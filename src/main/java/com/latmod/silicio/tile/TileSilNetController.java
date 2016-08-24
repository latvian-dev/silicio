package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.ISignalBus;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api.SilNet;
import com.latmod.silicio.api.impl.SignalBus;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileSilNetController extends TileSilNet implements ITickable, ISilNetController
{
    public final BaseTeslaContainer energyTank;
    private final SignalBus signalBus;
    private Collection<TileEntity> network;
    private boolean updateNetwork = true;

    public TileSilNetController()
    {
        energyTank = new BaseTeslaContainer(0, 500000, 200, 0);
        signalBus = new SignalBus();
        network = new ArrayList<>();
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.RERENDER;
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
    public void setControllerID(@Nullable UUID id, @Nonnull EntityPlayer playerIn)
    {
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        signalBus.read(nbt, "Signals");
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        signalBus.write(nbt, "Signals");
    }

    @Override
    public void readTileClientData(@Nonnull NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);
        signalBus.read(nbt, "S");
    }

    @Override
    public void writeTileClientData(@Nonnull NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);
        signalBus.write(nbt, "S");
    }

    @Override
    public void update()
    {
        if(getSide().isClient())
        {
            return;
        }

        if(updateNetwork)
        {
            network.clear();
            signalBus.clear();
            SilNet.findNetwork(network, getControllerID());
            network.remove(this);
            updateNetwork = false;
        }

        /*
        Map<Integer, Boolean> diffMap = new HashMap<>();
        Collection<SignalChannel> signalList0 = new HashSet<>(signalList.size());
        signalList0.addAll(signalList);
        signalList.clear();

        if(!modules.isEmpty())
        {
            Collection<SignalChannel> signalList1 = new HashSet<>();

            for(ModuleContainer c : modules)
            {
                c.onUpdate();

                if(c.module.getFlag(Module.FLAG_PROVIDE_SIGNALS))
                {
                    c.module.provideSignals(c, signalList1);

                    for(int i = 0; i < signalList1.size(); i++)
                    {
                        int id = signalList1.get(i);
                        if(id != 0 && !signalList.contains(id))
                        {
                            signalList.add(id);
                        }
                    }

                    signalList1.clear();
                }
            }
        }

        if(!diffMap.isEmpty())
        {
            FTBLib.printChat(null, "Signals: " + diffMap);

            if(!modules.isEmpty())
            {
                for(Map.Entry<Integer, Boolean> e : diffMap.entrySet())
                {
                }
            }
        }
        */

        checkIfDirty();
    }

    @Override
    public void onSilNetUpdate()
    {
        updateNetwork = true;
    }

    @Override
    public ISignalBus getSignalBus()
    {
        return signalBus;
    }

    @Override
    public Collection<TileEntity> getNetwork()
    {
        return network;
    }
}