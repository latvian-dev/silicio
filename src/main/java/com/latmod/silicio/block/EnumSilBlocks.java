package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.block.BlockWithVariants;
import com.latmod.lib.EnumNameMap;
import com.latmod.silicio.gui.SilGuis;
import com.latmod.silicio.tile.TileESU;
import com.latmod.silicio.tile.TileElemiteCrafter;
import com.latmod.silicio.tile.TileLamp;
import com.latmod.silicio.tile.TileModuleIO;
import com.latmod.silicio.tile.TileReactorCore;
import com.latmod.silicio.tile.TileSilNetController;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 09.08.2016.
 */
public enum EnumSilBlocks implements BlockWithVariants.IBlockVariant
{
    SILICON_BLOCK(0),
    SILICON_GLASS(1),
    DENSE_SILICON(2),
    SILICON_FRAME(3),
    ELEMITE_BLOCK(4),
    OPAL_BLOCK(5),
    OPAL_GLASS(6),
    CONTROLLER(7),
    ELEMITE_CRAFTER(8),
    MODULE_IO(9),
    ESU(10),
    COMPUTER_IO(11),
    REACTOR_CORE(12),
    LAMP(13);

    private final String name;
    private final int meta;

    EnumSilBlocks(int id)
    {
        name = EnumNameMap.createName(this);
        meta = id;
    }

    @Override
    public String getName()
    {
        return name;
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
    public Class<? extends TileEntity> getTileEntityClass()
    {
        switch(this)
        {
            case CONTROLLER:
                return TileSilNetController.class;
            case ELEMITE_CRAFTER:
                return TileElemiteCrafter.class;
            case MODULE_IO:
                return TileModuleIO.class;
            case ESU:
                return TileESU.class;
            case REACTOR_CORE:
                return TileReactorCore.class;
            case LAMP:
                return TileLamp.class;
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
            case ELEMITE_CRAFTER:
                return new TileElemiteCrafter();
            case MODULE_IO:
                return new TileModuleIO();
            case ESU:
                return new TileESU();
            case REACTOR_CORE:
                return new TileReactorCore();
            case LAMP:
                return new TileLamp();
            default:
                return null;
        }
    }

    @Override
    public Material getMaterial()
    {
        switch(this)
        {
            case SILICON_GLASS:
            case OPAL_GLASS:
                return Material.GLASS;
            case ELEMITE_BLOCK:
            case CONTROLLER:
            case ELEMITE_CRAFTER:
            case MODULE_IO:
            case ESU:
            case REACTOR_CORE:
                return Material.IRON;
            case LAMP:
                return Material.REDSTONE_LIGHT;
            default:
                return Material.ROCK;
        }
    }

    @Override
    public BlockRenderLayer getLayer()
    {
        switch(this)
        {
            case SILICON_BLOCK:
            case SILICON_GLASS:
            case OPAL_GLASS:
                return BlockRenderLayer.TRANSLUCENT;
            case SILICON_FRAME:
            case ESU:
            case REACTOR_CORE:
            case LAMP:
                return BlockRenderLayer.CUTOUT;
            default:
                return BlockRenderLayer.SOLID;
        }
    }

    @Override
    public boolean isFullCube()
    {
        return getLayer() == BlockRenderLayer.SOLID;
    }

    @Override
    public MapColor getMapColor()
    {
        switch(this)
        {
            case DENSE_SILICON:
            case MODULE_IO:
                return MapColor.BLACK;
            case SILICON_GLASS:
            case SILICON_FRAME:
                return MapColor.SILVER;
            case ELEMITE_BLOCK:
            case CONTROLLER:
            case ELEMITE_CRAFTER:
                return MapColor.BLUE;
            case ESU:
                return MapColor.LIGHT_BLUE;
            case COMPUTER_IO:
                return MapColor.YELLOW;
            default:
                return MapColor.GRAY;
        }
    }

    @Nullable
    public ResourceLocation getGuiID()
    {
        switch(this)
        {
            case MODULE_IO:
                return SilGuis.MODULE_IO;
            case ELEMITE_CRAFTER:
                return SilGuis.ELEMITE_CRAFTER;
            default:
                return null;
        }
    }

    @Override
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side)
    {
        ResourceLocation gui = getGuiID();

        if(gui != null)
        {
            if(!worldIn.isRemote)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("X", pos.getX());
                nbt.setInteger("Y", pos.getY());
                nbt.setInteger("Z", pos.getZ());
                FTBLibAPI.get().openGui(gui, playerIn, nbt);
            }

            return true;
        }
        else if(this == CONTROLLER)
        {
            if(!worldIn.isRemote)
            {
                TileEntity tile = worldIn.getTileEntity(pos);

                if(tile instanceof TileSilNetController)
                {
                    playerIn.addChatMessage(new TextComponentString("SilNet: " + ((TileSilNetController) tile).getNetwork().size()));
                }
            }

            return true;
        }

        return false;
    }
}
