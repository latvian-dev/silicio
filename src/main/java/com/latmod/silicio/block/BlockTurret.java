package com.latmod.silicio.block;

import com.latmod.lib.math.MathHelperLM;
import com.latmod.silicio.tile.TileTurret;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockTurret extends BlockSil
{
    public static final AxisAlignedBB[] BOXES = MathHelperLM.getRotatedBoxes(new AxisAlignedBB(1D / 16D, 0D, 1D / 16D, 15D / 16D, 10D / 16D, 15D / 16D));

    public BlockTurret()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.DOWN));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World w, IBlockState state)
    {
        return new TileTurret();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockDirectional.FACING).ordinal();
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOXES[state.getValue(BlockDirectional.FACING).ordinal()];
    }

    @Override
    @Deprecated
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(BlockDirectional.FACING, facing.getOpposite());
    }

    @Override
    @Deprecated
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    @Deprecated
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDirectional.FACING)));
    }
}
