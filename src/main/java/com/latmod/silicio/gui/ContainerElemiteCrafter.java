package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileElemiteCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerElemiteCrafter extends Container
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "elemite_crafter");

    public final TileElemiteCrafter tile;

    public ContainerElemiteCrafter(EntityPlayer ep, TileElemiteCrafter t)
    {
        tile = t;
        LMInvUtils.addPlayerSlots(this, ep, 8, 84, false);
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
        return LMInvUtils.transferStackInSlot(this, index, tile.itemHandler.getSlots());
    }
}
