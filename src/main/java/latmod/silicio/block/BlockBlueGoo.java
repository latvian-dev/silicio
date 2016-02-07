package latmod.silicio.block;

import latmod.silicio.item.ItemSilMaterials;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class BlockBlueGoo extends BlockSil
{
	public BlockBlueGoo(String s)
	{
		super(s, Material.clay);
		this.slipperiness = 0.8F;
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "GGG", "GGG", "GGG", 'G', ItemSilMaterials.BLUE_GOO.getStack());
		getMod().recipes.addShapelessRecipe(ItemSilMaterials.BLUE_GOO.getStack(9), this);
	}
	
	public TileEntity createNewTileEntity(World w, int m)
	{ return null; }
	
	public MapColor getMapColor(IBlockState state)
	{ return MapColor.grassColor; }
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.TRANSLUCENT; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		if(entityIn.isSneaking())
		{
			super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
		}
		else
		{
			entityIn.fall(fallDistance, 0F);
		}
	}
	
	public void onLanded(World worldIn, Entity entityIn)
	{
		if(entityIn.isSneaking())
		{
			super.onLanded(worldIn, entityIn);
		}
		else if(entityIn.motionY < 0D)
		{
			entityIn.motionY = -entityIn.motionY;
		}
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
	{
		if(Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
		{
			double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
			entityIn.motionX *= d0;
			entityIn.motionZ *= d0;
		}
		
		super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
	}
}