package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.feed_the_beast.ftbl.util.MathHelperMC;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileConnector;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class BlockConnector extends BlockSil
{
    public static final AxisAlignedBB[] BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(2D / 16D, 0D, 2D / 16D, 14D / 16D, 2D / 16D, 14D / 16D));
    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);

    public BlockConnector()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.NORTH));
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this), " C ", "WSW", 'W', SilItems.WIRE, 'C', SilItems.CIRCUIT_WIFI, 'S', ODItems.STONE);
    }

    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TileConnector.class, getRegistryName());
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World w, @Nonnull IBlockState state)
    {
        return new TileConnector();
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta % 6]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).ordinal();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Nonnull
    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOXES[state.getValue(FACING).ordinal()];
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}