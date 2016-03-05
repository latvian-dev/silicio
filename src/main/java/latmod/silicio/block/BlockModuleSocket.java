package latmod.silicio.block;

import latmod.silicio.tile.TileModuleSocket;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

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
	
	public BlockModuleSocket(String s)
	{
		super(s, Material.iron);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileModuleSocket.class, blockName);
	}
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileModuleSocket(); }
	
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState(); }
	
	public int getMetaFromState(IBlockState state)
	{ return 0; }
	
	protected BlockState createBlockState()
	{ return new BlockState(this, MODULE_D, MODULE_U, MODULE_N, MODULE_S, MODULE_W, MODULE_E); }
	
	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos)
	{
		boolean modD = false, modU = false, modN = false, modS = false, modW = false, modE = false;
		
		TileModuleSocket tile = (TileModuleSocket) w.getTileEntity(pos);
		
		if(tile != null)
		{
			modD = tile.modules.containsKey(EnumFacing.DOWN);
			modU = tile.modules.containsKey(EnumFacing.UP);
			modN = tile.modules.containsKey(EnumFacing.NORTH);
			modS = tile.modules.containsKey(EnumFacing.SOUTH);
			modW = tile.modules.containsKey(EnumFacing.WEST);
			modE = tile.modules.containsKey(EnumFacing.EAST);
		}
		
		return state.withProperty(MODULE_D, modD).withProperty(MODULE_U, modU).withProperty(MODULE_N, modN).withProperty(MODULE_S, modS).withProperty(MODULE_W, modW).withProperty(MODULE_E, modE);
	}
}
