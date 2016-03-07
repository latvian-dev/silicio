package latmod.silicio.block;

import ftb.lib.api.item.ODItems;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileCBController;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockController extends BlockSil
{
	public static final PropertyBool CONFLICT = PropertyBool.create("conflict");
	
	public BlockController(String s)
	{
		super(s, Material.iron);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileCBController.class, blockName);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " P ", "DFD", " P ", 'P', ItemSilMaterials.PROCESSOR.getStack(1), 'F', VariantBlocks2.SILICON_FRAME.getStack(1), 'D', ODItems.DIAMOND);
	}
	
	public IBlockState getModelState()
	{ return createBlockState().getBaseState().withProperty(CONFLICT, false); }
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileCBController(); }
	
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState(); }
	
	public int getMetaFromState(IBlockState state)
	{ return 0; }
	
	protected BlockState createBlockState()
	{ return new BlockState(this, CONFLICT); }
	
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