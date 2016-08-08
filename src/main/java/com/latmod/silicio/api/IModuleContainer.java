package com.latmod.silicio.api;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModuleContainer extends ITickable
{
    TileEntity getTile();

    EnumFacing getFacing();

    ItemStack getItem();

    IModule getModule();

    long getTick();

    void addConnection(EnumSignalSlot slot);

    boolean isChannelEnabled(EnumSignalSlot slot);

    int getChannel(EnumSignalSlot slot);
}