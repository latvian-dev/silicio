package latmod.silicio.block;

import ftb.lib.MathHelperMC;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileLaserMirrorBox;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockLaserMirrorBox extends BlockSil
{
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	
	public BlockLaserMirrorBox(String s)
	{
		super(s, Material.iron);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileLaserMirrorBox.class, blockName);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " G ", "GFL", " G ", 'G', VariantBlocks3.SILICON_GLASS.getStack(1), 'F', VariantBlocks2.SILICON_FRAME.getStack(1), 'L', ItemSilMaterials.LASER_LENS.getStack(1));
	}
	
	public IBlockState getModelState()
	{ return createBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH); }
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileLaserMirrorBox(); }
	
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
