package com.latmod.silicio.api.module;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModuleContainer extends ITickable
{
    TileEntity getTile();

    @Nullable
    EnumFacing getFacing();

    ItemStack getItem();

    IModule getModule();

    long getTick();

    void addProperty(IModulePropertyKey config);

    IModuleProperty getProperty(IModulePropertyKey config);

    Map<IModulePropertyKey, IModuleProperty> getProperties();
}