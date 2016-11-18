package com.latmod.silicio.api_impl.module;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.config.IConfigTree;
import com.feed_the_beast.ftbl.api.config.IConfigValue;
import com.feed_the_beast.ftbl.lib.config.ConfigTree;
import com.feed_the_beast.ftbl.lib.config.EmptyConfigTree;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISocketBlock;
import com.latmod.silicio.api_impl.SilCaps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by LatvianModder on 15.11.2016.
 */
public class ModuleContainer implements IModuleContainer, ICapabilityProvider, INBTSerializable<NBTTagCompound>
{
    private long tick;
    private IModule module;
    private IConfigTree properties;

    @Nullable
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
    public IConfigValue getProperty(IConfigKey config)
    {
        IConfigValue base = properties == null ? null : properties.get(config);
        return base == null ? config.getDefValue() : base;
    }

    @Override
    public IConfigTree getProperties()
    {
        if(properties == null)
        {
            return EmptyConfigTree.INSTANCE;
        }

        return properties;
    }

    public void loadProperties()
    {
        Collection<IConfigKey> propertyKeys = module == null ? Collections.emptyList() : module.getProperties();

        if(propertyKeys.isEmpty())
        {
            properties = null;
        }
        else
        {
            properties = new ConfigTree();

            for(IConfigKey key : propertyKeys)
            {
                properties.add(key, key.getDefValue().copy());
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == SilCaps.MODULE_CONTAINER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == SilCaps.MODULE_CONTAINER)
        {
            return (T) this;
        }

        return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        if(module != null)
        {
            nbt.setString("ID", module.getID().toString());
        }

        nbt.setLong("Tick", tick);

        if(properties != null && !properties.isEmpty())
        {
            nbt.setTag("Config", properties.serializeNBT());
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        module = Silicio.PROXY.moduleMap.get(nbt.getString("ID"));
        tick = nbt.getLong("Tick");
        properties = null;

        loadProperties();

        if(properties != null)
        {
            properties.deserializeNBT(nbt.getCompoundTag("Config"));
        }
    }

    @Override
    public void tick(ISocketBlock socketBlock)
    {
        if(module != null)
        {
            module.onUpdate(socketBlock);
        }

        tick++;
    }
}
