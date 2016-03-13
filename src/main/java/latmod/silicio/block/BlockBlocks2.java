package latmod.silicio.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockWithVariants;
import ftb.lib.api.item.ODItems;
import latmod.silicio.Silicio;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileReactorCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockBlocks2 extends BlockWithVariants<VariantBlocks2>
{
	public BlockBlocks2(String s)
	{
		super(s, Material.rock);
		setCreativeTab(Silicio.tab);
	}
	
	public Class<VariantBlocks2> getVariantType()
	{ return VariantBlocks2.class; }
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileReactorCore.class, VariantBlocks2.REACTOR_CORE.getName());
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(VariantBlocks2.SILICON_FRAME.getStack(1), "ISI", "S S", "ISI", 'S', VariantBlocks1.DENSE_SILICON.getStack(1), 'I', ODItems.IRON);
		getMod().recipes.addRecipe(VariantBlocks2.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', VariantBlocks2.SILICON_FRAME.getStack(1), 'A', ItemSilMaterials.ANTIMATTER.getStack(1), 'N', Items.nether_star, 'G', ODItems.GLOWSTONE);
	}
	
	public boolean hasTileEntity(IBlockState state)
	{
		VariantBlocks2 v = getVariant(state);
		return v == VariantBlocks2.REACTOR_CORE;
	}
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{
		VariantBlocks2 v = getVariant(state);
		if(v == VariantBlocks2.REACTOR_CORE) return new TileReactorCore();
		
		return null;
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{ return true; }
}