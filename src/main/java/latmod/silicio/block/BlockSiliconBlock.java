package latmod.silicio.block;

import latmod.silicio.item.ItemSilMaterials;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSiliconBlock extends BlockSil
{
	public BlockSiliconBlock(String s)
	{ super(s, Material.rock); }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "SS", "SS", 'S', ItemSilMaterials.SILICON.getStack(1));
		getMod().recipes.addShapelessRecipe(ItemSilMaterials.SILICON.getStack(4), this);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.TRANSLUCENT; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		return iblockstate.getBlock() != this && super.shouldSideBeRendered(worldIn, pos, side);
	}
}