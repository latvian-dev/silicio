package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileModuleIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerModuleIO extends Container
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "module_io");

    public TileModuleIO tile;
    private byte lastProgress = -1;
    private short lastEnergy = -1;

    public ContainerModuleIO(EntityPlayer ep, TileModuleIO t)
    {
        tile = t;
        addSlotToContainer(new SlotItemHandler(t.itemHandler, 0, 59, 11));
        addSlotToContainer(new SlotItemHandler(t.itemHandler, 1, 116, 11));
        LMInvUtils.addPlayerSlots(this, ep, 8, 39, false);
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

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(IContainerListener l : listeners)
        {
            if(lastProgress != tile.progress)
            {
                l.sendProgressBarUpdate(this, 0, tile.progress);
            }

            if(lastEnergy != tile.energy)
            {
                l.sendProgressBarUpdate(this, 1, tile.energy);
            }
        }

        lastProgress = tile.progress;
        lastEnergy = tile.energy;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        switch(id)
        {
            case 0:
                tile.progress = (byte) data;
                break;
            case 1:
                tile.energy = (short) data;
                break;
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id)
    {
        if(id == 0)
        {
            tile.startCopying();
            return true;
        }

        return false;
    }
}