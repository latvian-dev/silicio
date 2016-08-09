package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.BlockWithVariants;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 09.08.2016.
 */
public enum EnumSilBlocks implements BlockWithVariants.IBlockVariant
{
    SILICON_BLOCK(0),
    SILICON_GLASS(1),
    DENSE_SILICON(2),
    SILICON_FRAME(3),
    ELEMITE(4),
    HEX(5),
    HEX_COLOR(6);

    private final String name;
    private final int meta;

    EnumSilBlocks(int id)
    {
        name = name().toLowerCase();
        meta = id;
    }

    @Override
    public Item getItem()
    {
        return Item.getItemFromBlock(SilBlocks.BLOCKS);
    }

    @Override
    public int getMetadata()
    {
        return meta;
    }

    @Override
    @Nonnull
    public Material getMaterial()
    {
        switch(this)
        {
            case SILICON_GLASS:
                return Material.GLASS;
            case ELEMITE:
                return Material.IRON;
            default:
                return Material.ROCK;
        }
    }

    @Override
    @Nonnull
    public BlockRenderLayer getLayer()
    {
        switch(this)
        {
            case SILICON_BLOCK:
            case SILICON_GLASS:
                return BlockRenderLayer.TRANSLUCENT;
            case SILICON_FRAME:
                return BlockRenderLayer.CUTOUT;
            default:
                return BlockRenderLayer.SOLID;
        }
    }

    @Override
    @Nonnull
    public MapColor getMapColor()
    {
        switch(this)
        {
            case SILICON_BLOCK:
                return MapColor.GRAY;
            case SILICON_GLASS:
            case SILICON_FRAME:
                return MapColor.SILVER;
            case ELEMITE:
                return MapColor.BLUE;
            default:
                return MapColor.BLACK;
        }
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }
}
