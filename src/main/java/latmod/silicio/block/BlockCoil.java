package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.MathHelperMC;
import ftb.lib.api.item.ODItems;
import latmod.silicio.item.ItemSilMaterials;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class BlockCoil extends BlockSil
{
	public BlockCoil()
	{
		super(Material.ROCK);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "WIW", "WPW", "WPW", 'W', ItemSilMaterials.WIRE, 'I', ItemSilMaterials.ELEMITE_INGOT, 'P', ODItems.PLANKS);
	}
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(FACING, EnumFacing.NORTH); }
	
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
	{ return new BlockStateContainer(this, FACING); }
	
	@Override
	public int damageDropped(IBlockState state)
	{ return 0; }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{ return getDefaultState().withProperty(FACING, MathHelperMC.get3DRotation(pos, placer)); }
}