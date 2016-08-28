package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.SilicioAPI;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.IModuleProperty;
import com.latmod.silicio.api.module.IModulePropertyKey;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
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
    private long tick;
    private Map<IModulePropertyKey, IModuleProperty> properties;

    public ModuleContainer(TileEntity t)
    {
        tile = t;
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
    @Nullable
    public EnumFacing getFacing()
    {
        return facing;
    }

    public void setFacing(@Nullable EnumFacing f)
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
    public void addProperty(IModulePropertyKey config)
    {
        if(properties == null)
        {
            properties = new HashMap<>();
        }

        properties.put(config, config.getDefValue().copy());
    }

    @Override
    public IModuleProperty getProperty(IModulePropertyKey config)
    {
        IModuleProperty base = properties == null ? null : properties.get(config);
        return base == null ? config.getDefValue() : base;
    }

    @Override
    public Map<IModulePropertyKey, IModuleProperty> getProperties()
    {
        if(properties == null)
        {
            return Collections.emptyMap();
        }

        return properties;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setByte("Side", facing == null ? -1 : (byte) facing.ordinal());
        nbt.setLong("Tick", tick);

        NBTTagCompound tag2 = new NBTTagCompound();
        item.writeToNBT(tag2);
        nbt.setTag("Item", tag2);

        NBTTagCompound configTag = new NBTTagCompound();

        if(properties != null && !properties.isEmpty())
        {
            for(Map.Entry<IModulePropertyKey, IModuleProperty> entry : properties.entrySet())
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
        facing = nbt.getByte("Side") == -1 ? null : EnumFacing.VALUES[nbt.getByte("Side")];
        item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
        tick = nbt.getLong("Tick");
        properties = null;

        module = item.hasCapability(SilicioAPI.MODULE_PROVIDER, null) ? item.getCapability(SilicioAPI.MODULE_PROVIDER, null).getModule() : null;

        if(module != null)
        {
            module.init(this);
        }

        if(properties != null && !properties.isEmpty())
        {
            NBTTagCompound configTag = nbt.getCompoundTag("Config");

            for(Map.Entry<IModulePropertyKey, IModuleProperty> entry : properties.entrySet())
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