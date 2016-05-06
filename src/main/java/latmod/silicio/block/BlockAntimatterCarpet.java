package latmod.silicio.block;

import ftb.lib.FTBLib;
import latmod.silicio.item.SilItems;
import latmod.silicio.tile.TileAntimatter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAntimatterCarpet extends BlockSil
{
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 1D / 16D, 1D);
	
	public BlockAntimatterCarpet()
	{
		super(Material.CARPET);
		setLightLevel(1F);
		setHardness(0.2F);
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(TileAntimatter.class, getRegistryName());
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 3), "AA", 'A', SilItems.ANTIMATTER.getStack(1));
		getMod().recipes.addRecipe(SilItems.ANTIMATTER.getStack(4), "AAA", "AAA", 'A', this);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{ return EnumBlockRenderType.ENTITYBLOCK_ANIMATED; }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileAntimatter(); }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{ return false; }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{ return false; }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{ return AABB; }
}