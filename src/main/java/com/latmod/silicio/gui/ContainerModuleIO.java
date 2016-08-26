package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.gui.ContainerLM;
import com.latmod.silicio.tile.TileModuleIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerModuleIO extends ContainerLM
{
    public TileModuleIO tile;

    public ContainerModuleIO(EntityPlayer ep, TileModuleIO t)
    {
        super(ep);
        tile = t;

        addSlotToContainer(new SlotItemHandler(t.itemHandler, 0, 8, 8));
        addPlayerSlots(8, 84, false);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile.itemHandler;
    }
}