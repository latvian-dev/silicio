package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.BlockWithVariants;
import com.latmod.silicio.tile.TileESU;
import com.latmod.silicio.tile.TileReactorCore;
import com.latmod.silicio.tile.TileSilNetController;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 09.08.2016.
 */
public enum EnumSilMachines implements BlockWithVariants.IBlockVariant
{
    CONTROLLER(0),
    MODULE_COPIER(1),
    ESU(2),
    COMPUTER_IO(3),
    REACTOR_CORE(4);

    private final String name;
    private final int meta;

    EnumSilMachines(int id)
    {
        name = name().toLowerCase();
        meta = id;
    }

    @Override
    public Item getItem()
    {
        return Item.getItemFromBlock(SilBlocks.MACHINES);
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
        return Material.IRON;
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass()
    {
        switch(this)
        {
            case CONTROLLER:
                return TileSilNetController.class;
            case ESU:
                return TileESU.class;
            case REACTOR_CORE:
                return TileReactorCore.class;
            default:
                return null;
        }
    }

    @Override
    public TileEntity createTileEntity(World world)
    {
        switch(this)
        {
            case CONTROLLER:
                return new TileSilNetController();
            case ESU:
                return new TileESU();
            case REACTOR_CORE:
                return new TileReactorCore();
            default:
                return null;
        }
    }

    @Override
    @Nonnull
    public BlockRenderLayer getLayer()
    {
        switch(this)
        {
            case ESU:
            case REACTOR_CORE:
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
            case CONTROLLER:
                return MapColor.BLUE;
            case MODULE_COPIER:
                return MapColor.GRAY;
            case ESU:
                return MapColor.LIGHT_BLUE;
            case COMPUTER_IO:
                return MapColor.YELLOW;
            case REACTOR_CORE:
                return MapColor.GRAY;
            default:
                return MapColor.STONE;
        }
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }
}
