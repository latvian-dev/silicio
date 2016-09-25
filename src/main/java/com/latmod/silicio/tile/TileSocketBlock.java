package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.api_impl.module.SocketBlock;
import gnu.trove.map.TShortByteMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class TileSocketBlock extends TileSilNet implements ITickable
{
    private final Map<EnumFacing, SocketBlock> modules;

    public TileSocketBlock()
    {
        modules = new EnumMap<>(EnumFacing.class);

        for(EnumFacing f : EnumFacing.VALUES)
        {
            SocketBlock sb = new SocketBlock(this);
            sb.setFacing(f);
            modules.put(f, sb);
        }
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.RERENDER;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return (capability == SilCaps.SOCKET_BLOCK && modules.get(facing).hasContainer()) || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == SilCaps.SOCKET_BLOCK || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) modules.get(facing);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);

        NBTTagList list = new NBTTagList();

        for(SocketBlock sb : modules.values())
        {
            list.appendTag(sb.serializeNBT());
        }

        nbt.setTag("Modules", list);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);

        NBTTagList list = nbt.getTagList("Modules", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            modules.get(EnumFacing.VALUES[nbt1.getByte("Side")]).deserializeNBT(nbt1);
        }
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);

        NBTTagList list = new NBTTagList();

        for(SocketBlock sb : modules.values())
        {
            list.appendTag(sb.serializeNBT());
        }

        nbt.setTag("M", list);
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);

        NBTTagList list = nbt.getTagList("M", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            modules.get(EnumFacing.VALUES[nbt1.getByte("Side")]).deserializeNBT(nbt1);
        }
    }

    @Override
    public void update()
    {
        if(!worldObj.isRemote)
        {
            for(SocketBlock m : modules.values())
            {
                if(m.hasContainer())
                {
                    m.getContainer().tick(m);
                }
            }
        }

        checkIfDirty();
    }

    @Override
    public void provideSignals(ISilNetController controller)
    {
        for(SocketBlock m : modules.values())
        {
            if(m.hasContainer())
            {
                m.getContainer().getModule().provideSignals(m, controller);
            }
        }
    }

    @Override
    public void onSignalsChanged(ISilNetController controller, TShortByteMap channels)
    {
        for(SocketBlock m : modules.values())
        {
            if(m.hasContainer())
            {
                m.getContainer().getModule().onSignalsChanged(m, controller, channels);
            }
        }
    }
}