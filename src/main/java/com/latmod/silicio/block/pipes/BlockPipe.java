package com.latmod.silicio.block.pipes;

import com.latmod.silicio.block.BlockSil;
import com.latmod.silicio.tile.pipes.TilePipeBase;
import gnu.trove.list.array.TByteArrayList;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by LatvianModder on 03.01.2017.
 */
public class BlockPipe extends BlockSil
{
    private static final PropertyBool CON_D = PropertyBool.create("down");
    private static final PropertyBool CON_U = PropertyBool.create("up");
    private static final PropertyBool CON_N = PropertyBool.create("north");
    private static final PropertyBool CON_S = PropertyBool.create("south");
    private static final PropertyBool CON_W = PropertyBool.create("west");
    private static final PropertyBool CON_E = PropertyBool.create("east");

    public static final float BORDER_SIZE = 0.25F;
    private static final AxisAlignedBB BOXES[] = new AxisAlignedBB[7];
    private static final TByteArrayList TEMP_LIST = new TByteArrayList(6);

    static
    {
        double d0 = BORDER_SIZE - 1D / 32D;
        double d1 = 1D - d0;
        BOXES[0] = new AxisAlignedBB(d0, 0D, d0, d1, d0, d1);
        BOXES[1] = new AxisAlignedBB(d0, d1, d0, d1, 1D, d1);
        BOXES[2] = new AxisAlignedBB(d0, d0, 0D, d1, d1, d0);
        BOXES[3] = new AxisAlignedBB(d0, d0, d1, d1, d1, 1D);
        BOXES[4] = new AxisAlignedBB(0D, d0, d0, d0, d1, d1);
        BOXES[5] = new AxisAlignedBB(d1, d0, d0, 1D, d1, d1);
        BOXES[6] = new AxisAlignedBB(d0, d0, d0, d1, d1, d1);
    }

    public final EnumPipeType pipeType;
    public final EnumMK mark;

    public BlockPipe(EnumPipeType type, EnumMK mk)
    {
        super(Material.CIRCUITS);
        pipeType = type;
        mark = mk;
        setHardness(0.5F);
        setDefaultState(blockState.getBaseState().withProperty(CON_D, true).withProperty(CON_U, true).withProperty(CON_N, false).withProperty(CON_S, false).withProperty(CON_W, false).withProperty(CON_E, false));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return pipeType.tileSupplier != null;
    }

    @Override
    public TileEntity createTileEntity(World w, IBlockState state)
    {
        return pipeType.tileSupplier.get();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, CON_D, CON_U, CON_N, CON_S, CON_W, CON_E);
    }

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos)
    {
        TileEntity te = w.getTileEntity(pos);

        if(te instanceof TilePipeBase)
        {
            TilePipeBase pipe = (TilePipeBase) te;
            pipe.updatePipeConnections();
            boolean x0 = pipe.isPipeConnected(EnumFacing.WEST);
            boolean x1 = pipe.isPipeConnected(EnumFacing.EAST);
            boolean y0 = pipe.isPipeConnected(EnumFacing.DOWN);
            boolean y1 = pipe.isPipeConnected(EnumFacing.UP);
            boolean z0 = pipe.isPipeConnected(EnumFacing.NORTH);
            boolean z1 = pipe.isPipeConnected(EnumFacing.SOUTH);

            return state.withProperty(CON_D, y0).withProperty(CON_U, y1).withProperty(CON_W, x0).withProperty(CON_E, x1).withProperty(CON_N, z0).withProperty(CON_S, z1);
        }

        return state;
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOXES[6]);

        TileEntity te = worldIn.getTileEntity(pos);

        if(te instanceof TilePipeBase)
        {
            TilePipeBase pipe = (TilePipeBase) te;

            for(int i = 0; i < 6; i++)
            {
                if(pipe.isPipeConnected(EnumFacing.VALUES[i]))
                {
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, BOXES[i]);
                }
            }
        }
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        double s = BORDER_SIZE - 1D / 32D;

        TileEntity te = source.getTileEntity(pos);

        if(te instanceof TilePipeBase)
        {
            TilePipeBase pipe = (TilePipeBase) te;
            boolean x0 = pipe.isPipeConnected(EnumFacing.WEST);
            boolean x1 = pipe.isPipeConnected(EnumFacing.EAST);
            boolean y0 = pipe.isPipeConnected(EnumFacing.DOWN);
            boolean y1 = pipe.isPipeConnected(EnumFacing.UP);
            boolean z0 = pipe.isPipeConnected(EnumFacing.NORTH);
            boolean z1 = pipe.isPipeConnected(EnumFacing.SOUTH);
            return new AxisAlignedBB(x0 ? 0D : s, y0 ? 0D : s, z0 ? 0D : s, x1 ? 1D : 1D - s, y1 ? 1D : 1D - s, z1 ? 1D : 1D - s);
        }

        return new AxisAlignedBB(s, s, s, 1D - s, 1D - s, 1D - s);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof TilePipeBase && ((TilePipeBase) tileentity).onRightClick(playerIn, null, side);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if(tileentity instanceof TilePipeBase)
            {
                ((TilePipeBase) tileentity).onBroken();
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
}