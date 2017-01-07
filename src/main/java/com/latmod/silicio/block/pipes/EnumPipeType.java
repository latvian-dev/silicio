package com.latmod.silicio.block.pipes;

import com.feed_the_beast.ftbl.lib.EnumNameMap;
import com.latmod.silicio.tile.pipes.TileBasicPipe;
import com.latmod.silicio.tile.pipes.TileExtractionPipe;
import com.latmod.silicio.tile.pipes.TilePipeBase;
import com.latmod.silicio.tile.pipes.TileRoutingPipe;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public enum EnumPipeType implements IStringSerializable
{
    BASIC(3, TileBasicPipe::new),
    EXTRACTION(3, TileExtractionPipe::new),
    SUPPLIER(3, null),
    ROUTING(3, TileRoutingPipe::new),
    FILTER(3, null),
    STORAGE(3, null),
    CRAFTING(3, null),
    SATELLITE(3, null),
    BRIDGE(1, null),
    CROSSOVER(1, null),
    DISTRIBUTION(1, null);

    public static final EnumPipeType VALUES[] = values();

    public static void registerTiles()
    {
        for(EnumPipeType type : VALUES)
        {
            if(type.tileSupplier != null)
            {
                GameRegistry.registerTileEntity(type.tileSupplier.get().getClass(), "silicio.pipe." + type.getName());
            }
        }
    }

    private final String name;
    public final EnumMK marks[];
    public final Supplier<? extends TilePipeBase> tileSupplier;

    EnumPipeType(int maxmk, @Nullable Supplier<? extends TilePipeBase> tile)
    {
        name = EnumNameMap.createName(this);
        marks = new EnumMK[maxmk];
        tileSupplier = tile;

        for(int i = 0; i < marks.length; i++)
        {
            marks[i] = EnumMK.values()[i];
        }
    }

    @Override
    public String getName()
    {
        return name;
    }
}