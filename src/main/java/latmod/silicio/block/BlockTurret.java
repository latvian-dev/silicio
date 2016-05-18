package latmod.silicio.block;

import com.feed_the_beast.ftbl.util.BlockStateSerializer;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.feed_the_beast.ftbl.util.MathHelperMC;
import latmod.silicio.tile.TileTurret;
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
    public static final AxisAlignedBB[] BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(1D / 16D, 0D, 1D / 16D, 15D / 16D, 10D / 16D, 15D / 16D));
    
    public BlockTurret()
    {
        super(Material.IRON);
    }
    
    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TileTurret.class, getRegistryName());
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    { return true; }
    
    @Override
    public TileEntity createTileEntity(World w, IBlockState state)
    { return new TileTurret(); }
    
    @Override
    public void loadRecipes()
    {
    }
    
    @Override
    public int damageDropped(IBlockState state)
    { return 0; }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    { return false; }
    
    @Override
    public boolean isFullCube(IBlockState state)
    { return false; }
    
    @Override
    protected BlockStateContainer createBlockState()
    { return new BlockStateContainer(this, BlockDirectional.FACING); }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    { return getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.VALUES[meta]); }
    
    @Override
    public int getMetaFromState(IBlockState state)
    { return state.getValue(BlockDirectional.FACING).ordinal(); }
    
    @Override
    public String getModelState()
    { return BlockStateSerializer.getString(BlockDirectional.FACING, EnumFacing.DOWN); }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    { return BOXES[state.getValue(BlockDirectional.FACING).ordinal()]; }
    
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    { return getDefaultState().withProperty(BlockDirectional.FACING, facing.getOpposite()); }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    { return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING))); }
    
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    { return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDirectional.FACING))); }
}
