package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.lib.gui.ContainerLM;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileElemiteCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerElemiteCrafter extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "elemite_crafter");

    public final TileElemiteCrafter tile;

    public ContainerElemiteCrafter(EntityPlayer ep, TileElemiteCrafter t)
    {
        super(ep);
        tile = t;

        addPlayerSlots(8, 84, false);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile.itemHandler;
    }
}
