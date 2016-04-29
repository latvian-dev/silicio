package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import ftb.lib.MathHelperMC;
import latmod.silicio.tile.TileLaserRX;
import latmod.silicio.tile.TileLaserTX;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public class BlockLaserIO extends BlockSil
{
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	public final boolean isInput;
	
	public BlockLaserIO(boolean b)
	{
		super(Material.iron);
		isInput = b;
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(isInput ? TileLaserRX.class : TileLaserTX.class, getRegistryName());
	}
	
	@Override
	public void loadRecipes()
	{
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return isInput ? new TileLaserRX() : new TileLaserTX(); }
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(FACING, EnumFacing.NORTH); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{ return BlockRenderLayer.CUTOUT; }
	
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
	{ return getDefaultState().withProperty(FACING, MathHelperMC.get3DRotation(pos, placer)); }
}
