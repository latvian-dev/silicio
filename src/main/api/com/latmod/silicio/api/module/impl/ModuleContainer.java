package com.latmod.silicio.api.module.impl;

import com.latmod.silicio.api.SilicioAPI;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.IModuleProperty;
import com.latmod.silicio.api.module.IModulePropertyKey;
import com.latmod.silicio.api.tile.ISocketBlock;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LatvianModder on 1.09.2016.
 */
public class ModuleContainer implements IModuleContainer, ICapabilityProvider, INBTSerializable<NBTTagCompound>
{
    private final IModule module;
    private long tick;
    private Map<IModulePropertyKey, IModuleProperty> properties;

    public ModuleContainer(IModule m)
    {
        module = m;
    }

    @Override
    public IModule getModule()
    {
        return module;
    }

    @Override
    public long getTick()
    {
        return tick;
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

    public void loadProperties()
    {
        Collection<IModulePropertyKey> propertyKeys = getModule() == null ? null : module.getProperties();

        if(propertyKeys == null || propertyKeys.isEmpty())
        {
            properties = null;
        }
        else
        {
            properties = new HashMap<>(propertyKeys.size());

            for(IModulePropertyKey key : propertyKeys)
            {
                properties.put(key, key.getDefValue().copy());
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == SilicioAPI.MODULE_CONTAINER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == SilicioAPI.MODULE_CONTAINER)
        {
            return (T) this;
        }

        return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("Tick", tick);

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
        tick = nbt.getLong("Tick");
        properties = null;

        loadProperties();

        if(properties != null)
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

    @Override
    public void tick(ISocketBlock socketBlock)
    {
        module.onUpdate(socketBlock);
        tick++;
    }
}