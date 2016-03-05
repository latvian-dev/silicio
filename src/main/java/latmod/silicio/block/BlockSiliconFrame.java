package latmod.silicio.block;

import latmod.silicio.item.ItemSilMaterials;
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
		getMod().recipes.addRecipe(new ItemStack(this), "SSS", "S S", "SSS", 'S', ItemSilMaterials.SILICON.getStack(1));
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