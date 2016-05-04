package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.MathHelperMC;
import ftb.lib.api.item.ODItems;
import latmod.silicio.item.SilItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class BlockConnector extends BlockSil
{
	public static final AxisAlignedBB[] BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(2D / 16D, 0D, 2D / 16D, 14D / 16D, 2D / 16D, 14D / 16D), false);
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockConnector()
	{
		super(Material.ROCK);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " C ", "WSW", 'W', SilItems.WIRE, 'C', SilItems.CIRCUIT_WIFI, 'S', ODItems.STONE);
	}
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(FACING, EnumFacing.NORTH, ACTIVE, false); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{ return BlockRenderLayer.SOLID; }
	
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
	{ return new BlockStateContainer(this, FACING, ACTIVE); }
	
	@Override
	public int damageDropped(IBlockState state)
	{ return 0; }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{ return getDefaultState().withProperty(FACING, facing.getOpposite()); }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{ return BOXES[state.getValue(FACING).ordinal()]; }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{ return false; }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		//boolean active = false;
		//return state.withProperty(ACTIVE, active);
		
		return state;
	}
}