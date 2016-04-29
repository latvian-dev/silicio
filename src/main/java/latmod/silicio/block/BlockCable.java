package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import latmod.silicio.api.tileentity.ICBNetTile;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileCable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockCable extends BlockSil
{
	public static final PropertyBool CON_D = PropertyBool.create("down");
	public static final PropertyBool CON_U = PropertyBool.create("up");
	public static final PropertyBool CON_N = PropertyBool.create("north");
	public static final PropertyBool CON_S = PropertyBool.create("south");
	public static final PropertyBool CON_W = PropertyBool.create("west");
	public static final PropertyBool CON_E = PropertyBool.create("east");
	
	public static final float pipeBorder = 1F / 32F * 12F;
	public static final AxisAlignedBB boxes[] = new AxisAlignedBB[7];
	
	static
	{
		double d = pipeBorder - 1D / 32D;
		boxes[0] = new AxisAlignedBB(d, 0D, d, 1D - d, d, 1D - d);
		boxes[1] = new AxisAlignedBB(d, 1D - d, d, 1D - d, 1D, 1D - d);
		boxes[2] = new AxisAlignedBB(d, d, 0D, 1D - d, 1D - d, d);
		boxes[3] = new AxisAlignedBB(d, d, 1D - d, 1D - d, 1D - d, 1D);
		boxes[4] = new AxisAlignedBB(0D, d, d, d, 1D - d, 1D - d);
		boxes[5] = new AxisAlignedBB(1D - d, d, d, 1D, 1D - d, 1D - d);
		boxes[6] = new AxisAlignedBB(d, d, d, 1D - d, 1D - d, 1D - d);
	}
	
	public BlockCable()
	{
		super(Material.rock);
		setHardness(0.5F);
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(TileCable.class, getRegistryName());
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 8), "WWW", "WEW", "WWW", 'W', ItemSilMaterials.WIRE.getStack(1), 'E', ItemSilMaterials.ELEMITE_INGOT.getStack(1));
	}
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(CON_D, false, CON_U, false, CON_N, false, CON_S, false, CON_W, true, CON_E, true); }
	
	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{ return true; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{ return BlockRenderLayer.CUTOUT; }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{ return false; }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{ return false; }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileCable(); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{ return true; }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState(); }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return 0; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, CON_D, CON_U, CON_N, CON_S, CON_W, CON_E); }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos)
	{
		boolean conD = canConnectTo(w, pos, EnumFacing.DOWN);
		boolean conU = canConnectTo(w, pos, EnumFacing.UP);
		boolean conN = canConnectTo(w, pos, EnumFacing.NORTH);
		boolean conS = canConnectTo(w, pos, EnumFacing.SOUTH);
		boolean conW = canConnectTo(w, pos, EnumFacing.WEST);
		boolean conE = canConnectTo(w, pos, EnumFacing.EAST);
		
		/*
		TileEntity te = w.getTileEntity(pos);
		
		if(te != null && !te.isInvalid() && te instanceof TileCBCable)
		{
			active = ((TileCBCable)te).isActive();
		}*/
		
		return state.withProperty(CON_D, conD).withProperty(CON_U, conU).withProperty(CON_N, conN).withProperty(CON_S, conS).withProperty(CON_W, conW).withProperty(CON_E, conE);
	}
	
	public boolean canConnectTo(IBlockAccess w, BlockPos pos0, EnumFacing facing)
	{
		BlockPos pos = pos0.offset(facing);
		IBlockState state = w.getBlockState(pos);
		if(state.getBlock() == this) return true;
		
		if(state.getBlock().hasTileEntity(state))
		{
			TileEntity te = w.getTileEntity(pos);
			
			/*if(te instanceof IMultipartContainer)
			{
				for(IMultipart m : ((IMultipartContainer) te).getParts())
				{
					if(m instanceof MultipartCable) return true;
				}
			}*/
			
			if(te instanceof ICBNetTile)
			{
				return ((ICBNetTile) te).canCBConnect(facing.getOpposite());
			}
		}
		
		return false;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World w, BlockPos pos, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity entity)
	{
		addCollisionBoxToList(pos, mask, list, boxes[6]);
		
		for(int i = 0; i < 6; i++)
		{
			if(canConnectTo(w, pos, EnumFacing.VALUES[i]))
			{
				addCollisionBoxToList(pos, mask, list, boxes[i]);
			}
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess w, BlockPos pos)
	{
		//TODO: Cache
		float s = pipeBorder - 1 / 32F;
		
		boolean x0 = canConnectTo(w, pos, EnumFacing.WEST);
		boolean x1 = canConnectTo(w, pos, EnumFacing.EAST);
		boolean y0 = canConnectTo(w, pos, EnumFacing.DOWN);
		boolean y1 = canConnectTo(w, pos, EnumFacing.UP);
		boolean z0 = canConnectTo(w, pos, EnumFacing.NORTH);
		boolean z1 = canConnectTo(w, pos, EnumFacing.SOUTH);
		
		return new AxisAlignedBB(x0 ? 0F : s, y0 ? 0F : s, z0 ? 0F : s, x1 ? 1F : 1F - s, y1 ? 1F : 1F - s, z1 ? 1F : 1F - s);
	}
}