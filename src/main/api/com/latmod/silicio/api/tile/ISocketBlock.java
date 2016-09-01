package com.latmod.silicio.api.tile;

import com.latmod.silicio.api.module.IModuleContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface ISocketBlock
{
    TileEntity getTile();

    @Nullable
    EnumFacing getFacing();

    ItemStack getStack();

    IModuleContainer getContainer();
}