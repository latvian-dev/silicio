package latmod.silicio.block;

import ftb.lib.api.item.ODItems;
import latmod.silicio.SilItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSiliconGlass extends BlockSil
{
	public BlockSiliconGlass(String s)
	{ super(s, Material.glass); }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		ODItems.add(ODItems.GLASS, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addSmelting(new ItemStack(this), new ItemStack(SilItems.b_silicon));
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.TRANSLUCENT; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public TileEntity createNewTileEntity(World w, int m)
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		return iblockstate.getBlock() != this && super.shouldSideBeRendered(worldIn, pos, side);
	}
}