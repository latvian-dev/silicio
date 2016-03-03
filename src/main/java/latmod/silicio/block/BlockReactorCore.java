package latmod.silicio.block;

import latmod.silicio.tile.TileReactorCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockReactorCore extends BlockSil
{
	public BlockReactorCore(String s)
	{ super(s, Material.iron); }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " S ", "SNS", " S ", 'S', BlockSimpleBlocks.EnumType.dense_silicon.getStack(1), 'N', Items.nether_star);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileReactorCore.class, blockName);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.SOLID; }
	
	public boolean isOpaqueCube()
	{ return true; }
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileReactorCore(); }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		return iblockstate.getBlock() != this && super.shouldSideBeRendered(worldIn, pos, side);
	}
}