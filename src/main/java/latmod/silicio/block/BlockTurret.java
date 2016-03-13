package latmod.silicio.block;

import latmod.silicio.tile.TileTurret;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockTurret extends BlockSil
{
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	
	public BlockTurret(String s)
	{
		super(s, Material.iron);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileTurret.class, blockName);
	}
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileTurret(); }
	
	public void loadRecipes()
	{
	}
	
	public IBlockState getModelState()
	{ return createBlockState().getBaseState().withProperty(FACING, EnumFacing.UP); }
	
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
	{ return getDefaultState().withProperty(FACING, facing); }
}
