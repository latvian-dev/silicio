package latmod.silicio.block;

import ftb.lib.mod.FTBLibMod;
import latmod.silicio.api.tileentity.ICBNetTile;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileCable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

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
	
	public BlockCable(String s)
	{
		super(s, Material.rock);
		setHardness(0.5F);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileCable.class, blockName);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 8), "WWW", "NNN", "WWW", 'N', ItemSilMaterials.ELEMITE_NUGGET.getStack(1), 'W', Blocks.carpet);
	}
	
	public void loadModels()
	{
		FTBLibMod.proxy.addItemModel(getMod().getID(), getItem(), 0, blockName, "north=true,south=true");
	}
	
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.CUTOUT_MIPPED; }
	
	public boolean isFullCube()
	{ return false; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	public TileEntity createTileEntity(World w, IBlockState state)
	{ return new TileCable(); }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{ return true; }
	
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState(); }
	
	public int getMetaFromState(IBlockState state)
	{ return 0; }
	
	protected BlockState createBlockState()
	{ return new BlockState(this, CON_D, CON_U, CON_N, CON_S, CON_W, CON_E); }
	
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
	
	public void setBlockBoundsForItemRender()
	{
		float s = pipeBorder;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
	}
	
	public void addCollisionBoxesToList(World w, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity e)
	{
		double ox = pos.getX();
		double oy = pos.getY();
		double oz = pos.getZ();
		
		addIfIntersects(list, mask, boxes[6], ox, oy, oz);
		
		for(int i = 0; i < 6; i++)
		{
			if(canConnectTo(w, pos, EnumFacing.VALUES[i]))
			{
				addIfIntersects(list, mask, boxes[i], ox, oy, oz);
			}
		}
	}
	
	private static void addIfIntersects(List<AxisAlignedBB> list, AxisAlignedBB mask, AxisAlignedBB box, double ox, double oy, double oz)
	{
		AxisAlignedBB box1 = box.offset(ox, oy, oz);
		if(mask.intersectsWith(box1)) list.add(box1);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess w, BlockPos pos)
	{
		float s = pipeBorder - 1 / 32F;
		
		boolean x0 = canConnectTo(w, pos, EnumFacing.WEST);
		boolean x1 = canConnectTo(w, pos, EnumFacing.EAST);
		boolean y0 = canConnectTo(w, pos, EnumFacing.DOWN);
		boolean y1 = canConnectTo(w, pos, EnumFacing.UP);
		boolean z0 = canConnectTo(w, pos, EnumFacing.NORTH);
		boolean z1 = canConnectTo(w, pos, EnumFacing.SOUTH);
		
		setBlockBounds(x0 ? 0F : s, y0 ? 0F : s, z0 ? 0F : s, x1 ? 1F : 1F - s, y1 ? 1F : 1F - s, z1 ? 1F : 1F - s);
	}
}