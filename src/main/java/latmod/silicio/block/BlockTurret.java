package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import ftb.lib.MathHelperMC;
import latmod.silicio.tile.TileTurret;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockTurret extends BlockSil
{
	public static final AxisAlignedBB[] BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(1D / 16D, 0D, 1D / 16D, 15D / 16D, 7D / 16D, 15D / 16D), true);
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	
	public BlockTurret()
	{
		super(Material.IRON);
	}
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
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
	public String getModelState()
	{ return BlockStateSerializer.getString(FACING, EnumFacing.UP); }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{ return false; }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta % 6]); }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return state.getValue(FACING).ordinal(); }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, FACING); }
	
	@Override
	public int damageDropped(IBlockState state)
	{ return 0; }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{ return getDefaultState().withProperty(FACING, facing); }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{ return BOXES[state.getValue(FACING).ordinal()]; }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{ return false; }
}
