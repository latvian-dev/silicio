package latmod.silicio.block;

import ftb.lib.api.item.ODItems;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSiliconFrame extends BlockSil
{
	public BlockSiliconFrame(String s)
	{ super(s, Material.rock); }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "ISI", "S S", "ISI", 'S', BlockSimpleBlocks.EnumType.dense_silicon.getStack(1), 'I', ODItems.IRON);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.CUTOUT; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{ return true; }
}