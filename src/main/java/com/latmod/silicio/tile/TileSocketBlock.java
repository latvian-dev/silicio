package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.module.impl.ModuleContainer;
import com.latmod.silicio.api.tile.ISilNetController;
import gnu.trove.map.TIntByteMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class TileSocketBlock extends TileSilNet implements ITickable
{
    public final Map<EnumFacing, ModuleContainer> modules;

    public TileSocketBlock()
    {
        modules = new EnumMap<>(EnumFacing.class);
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.RERENDER;
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);

        NBTTagList list = new NBTTagList();

        for(ModuleContainer m : modules.values())
        {
            list.appendTag(m.serializeNBT());
        }

        nbt.setTag("Modules", list);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);

        modules.clear();

        NBTTagList list = nbt.getTagList("Modules", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++)
        {
            ModuleContainer c = new ModuleContainer(this);
            c.deserializeNBT(list.getCompoundTagAt(i));

            if(c.getModule() != null)
            {
                modules.put(c.getFacing(), c);
            }
        }
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);

        NBTTagList list = new NBTTagList();

        for(ModuleContainer m : modules.values())
        {
            list.appendTag(m.serializeNBT());
        }

        nbt.setTag("M", list);
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);

        modules.clear();

        NBTTagList list = nbt.getTagList("M", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++)
        {
            ModuleContainer c = new ModuleContainer(this);
            c.deserializeNBT(list.getCompoundTagAt(i));

            if(c.getModule() != null)
            {
                modules.put(c.getFacing(), c);
                c.getModule().init(c);
            }
        }
    }

    @Override
    public void update()
    {
        if(!worldObj.isRemote)
        {
            if(!modules.isEmpty())
            {
                for(ModuleContainer m : modules.values())
                {
                    m.update();
                }
            }
        }

        checkIfDirty();
    }

    @Override
    public void provideSignals(ISilNetController controller)
    {
        if(!modules.isEmpty())
        {
            for(ModuleContainer m : modules.values())
            {
                m.getModule().provideSignals(m, controller);
            }
        }
    }

    @Override
    public void onSignalsChanged(ISilNetController controller, TIntByteMap channels)
    {
        if(!modules.isEmpty())
        {
            for(ModuleContainer m : modules.values())
            {
                m.getModule().onSignalsChanged(m, controller, channels);
            }
        }
    }
}