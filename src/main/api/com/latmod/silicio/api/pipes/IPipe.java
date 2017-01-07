package com.latmod.silicio.api.pipes;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public interface IPipe
{
    @Nullable
    ItemStack insertItemInPipe(TransportedItem item, boolean simulate);
}