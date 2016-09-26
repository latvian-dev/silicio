package com.latmod.silicio.api.module;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by LatvianModder on 01.09.2016.
 */
public class ModuleBase implements IModule
{
    private final ResourceLocation ID;
    protected final Collection<IConfigKey> properties;
    private ModelResourceLocation model;
    private String unlocalizedName;

    public ModuleBase(ResourceLocation id)
    {
        ID = id;
        properties = new ArrayList<>();
        unlocalizedName = id.getResourceDomain() + ".item.module." + id.getResourcePath();
    }

    protected void setUnlocalizedName(String s)
    {
        unlocalizedName = s;
    }

    @Override
    public ResourceLocation getID()
    {
        return ID;
    }

    @Override
    public Collection<IConfigKey> getProperties()
    {
        return properties.isEmpty() ? Collections.emptyList() : Collections.unmodifiableCollection(properties);
    }

    @Override
    public ModelResourceLocation getModelLocation()
    {
        if(model == null)
        {
            model = new ModelResourceLocation(new ResourceLocation(getID().getResourceDomain(), "modules/" + getID().getResourcePath()), "inventory");
        }

        return model;
    }

    @Override
    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }
}