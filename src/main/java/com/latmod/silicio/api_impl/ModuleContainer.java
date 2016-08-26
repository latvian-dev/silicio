package com.latmod.silicio.api_impl;

import com.latmod.silicio.api.EnumSignalSlot;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleContainer;
import com.latmod.silicio.api.IModuleProperty;
import com.latmod.silicio.api.IModulePropertyKey;
import com.latmod.silicio.api.SilicioAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
@ParametersAreNonnullByDefault
public final class ModuleContainer implements IModuleContainer, INBTSerializable<NBTTagCompound>
{
    private final TileEntity tile;
    private EnumFacing facing;
    private ItemStack item;
    private IModule module;
    private Map<EnumSignalSlot, Integer> connections;
    private long tick;
    private Map<IModulePropertyKey<?>, IModuleProperty> configMap;

    public ModuleContainer(TileEntity t)
    {
        tile = t;
        connections = new HashMap<>();
    }

    @Override
    public void update()
    {
        module.onUpdate(this);
        tick++;
    }

    @Override
    public TileEntity getTile()
    {
        return tile;
    }

    @Override
    public EnumFacing getFacing()
    {
        return facing;
    }

    public void setFacing(EnumFacing f)
    {
        facing = f;
    }

    @Override
    public ItemStack getItem()
    {
        return item;
    }

    public void setItem(ItemStack is)
    {
        item = is;
    }

    @Override
    public IModule getModule()
    {
        return module;
    }

    public void setModule(IModule m)
    {
        module = m;
    }

    @Override
    public long getTick()
    {
        return tick;
    }

    @Override
    public void addConnection(EnumSignalSlot slot)
    {
        connections.put(slot, null);
    }

    @Override
    public boolean isChannelEnabled(EnumSignalSlot slot)
    {
        return connections.get(slot) != null;
    }

    @Override
    public int getChannel(EnumSignalSlot slot)
    {
        return isChannelEnabled(slot) ? connections.get(slot) : 0;
    }

    @Override
    public void addProperty(IModulePropertyKey config)
    {
        if(configMap == null)
        {
            configMap = new HashMap<>();
        }

        configMap.put(config, null);
    }

    @Override
    public <N extends IModuleProperty> N getProperty(IModulePropertyKey<N> config)
    {
        IModuleProperty base = configMap == null ? null : configMap.get(config);
        return base == null ? config.getDefValue() : (N) base;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("Side", (byte) facing.ordinal());
        nbt.setLong("Tick", tick);

        NBTTagCompound tag2 = new NBTTagCompound();
        item.writeToNBT(tag2);
        nbt.setTag("Item", tag2);

        NBTTagCompound configTag = new NBTTagCompound();

        if(configMap != null && !configMap.isEmpty())
        {
            for(Map.Entry<IModulePropertyKey<?>, IModuleProperty> entry : configMap.entrySet())
            {
                if(entry.getValue() != null)
                {
                    configTag.setTag(entry.getKey().getName(), entry.getValue().serializeNBT());
                }
            }
        }

        nbt.setTag("Config", configTag);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        facing = EnumFacing.VALUES[nbt.getByte("Side")];
        item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
        tick = nbt.getLong("Tick");

        if(item.hasCapability(SilicioAPI.MODULE_PROVIDER, null))
        {
            module = item.getCapability(SilicioAPI.MODULE_PROVIDER, null).getModule();
        }

        if(configMap != null && !configMap.isEmpty())
        {
            NBTTagCompound configTag = nbt.getCompoundTag("Config");

            for(Map.Entry<IModulePropertyKey<?>, IModuleProperty> entry : configMap.entrySet())
            {
                NBTBase base = configTag.getTag(entry.getKey().getName());

                if(base != null)
                {
                    entry.getValue().deserializeNBT(base);
                }
            }
        }
    }
}