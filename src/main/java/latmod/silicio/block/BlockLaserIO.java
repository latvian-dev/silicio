package latmod.silicio.block;

import ftb.lib.MathHelperMC;
import latmod.silicio.tile.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public class BlockLaserIO extends BlockSil
{
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	public final boolean isInput;
	
	public BlockLaserIO(String s, boolean b)
	{
		super(s, Material.iron);
		isInput = b;
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(isInput ? TileLaserRX.class : TileLaserTX.class, blockName);
	}
	
	public void loadRecipes()
	{
	}
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return isInput ? new TileLaserRX() : new TileLaserTX(); }
	
	public IBlockState getModelState()
	{ return createBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH); }
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.CUTOUT; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta % 6]); }
	
	public int getMetaFromState(IBlockState state)
	{ return state.getValue(FACING).ordinal(); }
	
	protected BlockState createBlockState()
	{ return new BlockState(this, FACING); }
	
	public int damageDropped(IBlockState state)
	{ return 0; }
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{ return getDefaultState().withProperty(FACING, MathHelperMC.get3DRotation(worldIn, pos, placer)); }
}
