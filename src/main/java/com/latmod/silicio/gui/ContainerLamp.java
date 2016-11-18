package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.lib.gui.ContainerLM;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileLamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.09.2016.
 */
public class ContainerLamp extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "lamp");

    public TileLamp tile;

    public ContainerLamp(EntityPlayer ep, TileLamp t)
    {
        super(ep);
        tile = t;
        addPlayerSlots(8, 76, false);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return null;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }
}