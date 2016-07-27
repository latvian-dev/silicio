package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.lib.util.LMListUtils;
import com.latmod.silicio.api.SignalChannel;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.energy.SilEnergyTank;
import com.latmod.silicio.block.BlockConnector;
import com.latmod.silicio.block.SilBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileSilNetController extends TileSilNet implements ITickable, ISilNetController
{
    public final SilEnergyTank energyTank;
    private final Collection<SignalChannel> signalList;
    private Collection<BlockPos> network;
    private Collection<TileEntity> connectedTileEntities;
    private boolean updateNetwork = true;

    public TileSilNetController()
    {
        energyTank = new SilEnergyTank(100000D);
        signalList = new HashSet<>();
        network = new HashSet<>();
        connectedTileEntities = new HashSet<>();
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
        signalList.clear();

        for(int i : tag.getIntArray("Signals"))
        {
            signalList.add(new SignalChannel(i));
        }
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound tag)
    {
        super.writeTileData(tag);
        tag.setIntArray("Signals", LMListUtils.toHashCodeArray(signalList));
    }

    @Override
    public void readTileClientData(@Nonnull NBTTagCompound tag)
    {
        super.readTileClientData(tag);

        signalList.clear();

        for(int i : tag.getIntArray("S"))
        {
            signalList.add(new SignalChannel(i));
        }
    }

    @Override
    public void writeTileClientData(@Nonnull NBTTagCompound tag)
    {
        super.writeTileClientData(tag);
        tag.setIntArray("S", LMListUtils.toHashCodeArray(signalList));
    }

    @Override
    public void onLoad()
    {
        super.onLoad();

        System.out.println(getWorld());
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
            connectedTileEntities.clear();
            signalList.clear();

            if(!network.isEmpty())
            {
                for(BlockPos bpos : network)
                {
                    IBlockState state = worldObj.getBlockState(bpos);

                    if(state.getBlock() == SilBlocks.CONNECTOR)
                    {
                        TileEntity te = worldObj.getTileEntity(bpos.offset(state.getValue(BlockConnector.FACING)));

                        if(te != null)
                        {
                            connectedTileEntities.add(te);
                        }
                    }
                }
            }
            else
            {

            }

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
    public boolean getSignalState(SignalChannel c)
    {
        return c != null && !c.isInvalid() && signalList.contains(c);
    }

    @Override
    public void addToNetwork(BlockPos pos)
    {
        network.add(pos);
    }

    @Override
    public Collection<BlockPos> getNetwork()
    {
        return network;
    }
}