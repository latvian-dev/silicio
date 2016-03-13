package latmod.silicio.block;

import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileAntimatter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAntimatter extends BlockSil
{
	public BlockAntimatter(String s)
	{
		super(s, Material.carpet);
		setLightLevel(1F);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 16F, 1F);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileAntimatter.class, blockName);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 3), "AA", 'A', ItemSilMaterials.ANTIMATTER.getStack(1));
		getMod().recipes.addRecipe(ItemSilMaterials.ANTIMATTER.getStack(4), "AAA", "AAA", 'A', this);
	}
	
	public int getRenderType()
	{ return 2; }
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileAntimatter(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean isFullCube()
	{ return false; }
}