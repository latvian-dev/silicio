package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileLamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.09.2016.
 */
public class ContainerLamp extends Container
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "lamp");

    public TileLamp tile;

    public ContainerLamp(EntityPlayer ep, TileLamp t)
    {
        tile = t;
        LMInvUtils.addPlayerSlots(this, ep, 8, 76, false);
    }

    @Override
    public boolean canInteractWith(EntityPlayer ep)
    {
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }
}