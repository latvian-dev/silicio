package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.ISignalBus;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api.SilNet;
import com.latmod.silicio.api.impl.SignalBus;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

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
    public void readTileData(@Nonnull NBTTagCompound tag)
    {
        super.readTileData(tag);
        signalBus.read(tag, "Signals");
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound tag)
    {
        super.writeTileData(tag);
        signalBus.write(tag, "Signals");
    }

    @Override
    public void readTileClientData(@Nonnull NBTTagCompound tag)
    {
        super.readTileClientData(tag);
        signalBus.read(tag, "S");
    }

    @Override
    public void writeTileClientData(@Nonnull NBTTagCompound tag)
    {
        super.writeTileClientData(tag);
        signalBus.write(tag, "S");
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

            SilNet.findNetwork(this, network);

            /*
            if(!network.isEmpty())
            {
                for(TileEntity tile : network)
                {
                }
            }*/

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