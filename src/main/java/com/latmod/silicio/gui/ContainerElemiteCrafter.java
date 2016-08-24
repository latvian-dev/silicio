package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.gui.ContainerLM;
import com.latmod.silicio.tile.TileElemiteCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerElemiteCrafter extends ContainerLM
{
    public final TileElemiteCrafter tile;

    public ContainerElemiteCrafter(EntityPlayer ep, TileElemiteCrafter t)
    {
        super(ep);
        tile = t;
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile.itemHandler;
    }
}
