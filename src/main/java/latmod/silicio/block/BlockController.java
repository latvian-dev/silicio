package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import ftb.lib.api.item.ODItems;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileCBController;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockController extends BlockSil
{
	public static final PropertyBool CONFLICT = PropertyBool.create("conflict");
	
	public BlockController()
	{
		super(Material.iron);
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(TileCBController.class, getRegistryName());
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " P ", "DFD", " P ", 'P', ItemSilMaterials.PROCESSOR.getStack(1), 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1), 'D', ODItems.DIAMOND);
	}
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(CONFLICT, false); }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileCBController(); }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState(); }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return 0; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, CONFLICT); }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos)
	{
		boolean conflict = false;
		
		TileCBController tile = (TileCBController) w.getTileEntity(pos);
		
		if(tile != null)
		{
			conflict = tile.hasConflict();
		}
		
		return state.withProperty(CONFLICT, conflict);
	}
}
