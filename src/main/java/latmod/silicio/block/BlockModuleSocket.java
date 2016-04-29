package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileModuleSocket;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockModuleSocket extends BlockSil
{
	public static final PropertyBool MODULE_D = PropertyBool.create("down");
	public static final PropertyBool MODULE_U = PropertyBool.create("up");
	public static final PropertyBool MODULE_N = PropertyBool.create("north");
	public static final PropertyBool MODULE_S = PropertyBool.create("south");
	public static final PropertyBool MODULE_W = PropertyBool.create("west");
	public static final PropertyBool MODULE_E = PropertyBool.create("east");
	public static final PropertyBool CENTER = PropertyBool.create("center");
	
	public BlockModuleSocket()
	{
		super(Material.iron);
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(TileModuleSocket.class, getRegistryName());
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " P ", "PFP", " P ", 'P', ItemSilMaterials.PROCESSOR.getStack(1), 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1));
	}
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(MODULE_D, false, MODULE_U, false, MODULE_N, false, MODULE_S, false, MODULE_W, false, MODULE_E, false, CENTER, true); }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileModuleSocket(); }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState(); }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return 0; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, MODULE_D, MODULE_U, MODULE_N, MODULE_S, MODULE_W, MODULE_E, CENTER); }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos)
	{
		boolean modD = false, modU = false, modN = false, modS = false, modW = false, modE = false;
		
		TileModuleSocket tile = (TileModuleSocket) w.getTileEntity(pos);
		
		if(tile != null)
		{
			modD = tile.hasModules(EnumFacing.DOWN);
			modU = tile.hasModules(EnumFacing.UP);
			modN = tile.hasModules(EnumFacing.NORTH);
			modS = tile.hasModules(EnumFacing.SOUTH);
			modW = tile.hasModules(EnumFacing.WEST);
			modE = tile.hasModules(EnumFacing.EAST);
		}
		
		return state.withProperty(MODULE_D, modD).withProperty(MODULE_U, modU).withProperty(MODULE_N, modN).withProperty(MODULE_S, modS).withProperty(MODULE_W, modW).withProperty(MODULE_E, modE).withProperty(CENTER, true);
	}
}
