package latmod.silicio.block;
import java.util.List;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.core.util.*;
import latmod.silicio.client.render.world.RenderCBCable;
import latmod.silicio.tile.TileCBCable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockCBCable extends BlockSil
{
	public static final float pipeBorder = 1F / 32F * 12F;
	private static final AxisAlignedBB boxes[] = new AxisAlignedBB[7];
	
	static
	{
		double d = pipeBorder;
		boxes[0] = AxisAlignedBB.getBoundingBox(d, 0D, d, 1D - d, d, 1D - d);
		boxes[1] = AxisAlignedBB.getBoundingBox(d, 1D - d, d, 1D - d, 1D, 1D - d);
		boxes[2] = AxisAlignedBB.getBoundingBox(d, d, 0D, 1D - d, 1D - d, d);
		boxes[3] = AxisAlignedBB.getBoundingBox(d, d, 1D - d, 1D - d, 1D - d, 1D);
		boxes[4] = AxisAlignedBB.getBoundingBox(0D, d, d, d, 1D - d, 1D - d);
		boxes[5] = AxisAlignedBB.getBoundingBox(1D - d, d, d, 1D, 1D - d, 1D - d);
		boxes[6] = AxisAlignedBB.getBoundingBox(d, d, d, 1D - d, 1D - d, 1D - d);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_board, icon_cover;
	
	public BlockCBCable(String s)
	{
		super(s, Material.iron);
		setHardness(0.4F);
		isBlockContainer = true;
		mod.addTile(TileCBCable.class, s);
	}
	
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{ return true; }
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileCBCable(); }
	
	public void loadRecipes()
	{
		if(ODItems.hasOre(ODItems.RUBBER))
		{
			mod.recipes.addRecipe(new ItemStack(this, 16), "RRR", "SES", "RRR",
					'R', ODItems.RUBBER,
					'E', ODItems.SILVER,
					'S', ODItems.REDSTONE);
		}
		else
		{
			mod.recipes.addRecipe(new ItemStack(this, 16), "RRR", "SES", "RRR",
					'R', ODItems.SLIMEBALL,
					'E', ODItems.SILVER,
					'S', ODItems.REDSTONE);
		}
	}
	
	public void setBlockBoundsForItemRender()
	{
		float s = pipeBorder;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		super.addCollisionBoxesToList(w, x, y, z, bb, l, e);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		TileEntity te = iba.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileCBCable)
		{
			TileCBCable t = (TileCBCable)te;
			
			if(t.isInvalid() || t.hasCover)
			{
				setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
				return;
			}
			
			float s = pipeBorder;// - 1 / 16F;
			
			boolean x0 = TileCBCable.connectCable(t, 4);
			boolean x1 = TileCBCable.connectCable(t, 5);
			boolean y0 = TileCBCable.connectCable(t, 0);
			boolean y1 = TileCBCable.connectCable(t, 1);
			boolean z0 = TileCBCable.connectCable(t, 2);
			boolean z1 = TileCBCable.connectCable(t, 3);
			
			setBlockBounds(x0 ? 0F : s, y0 ? 0F : s, z0 ? 0F: s, x1 ? 1F : 1F - s, y1 ? 1F: 1F - s, z1 ? 1F : 1F - s);
		}
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderCBCable.instance.getRenderId(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "cable");
		icon_board = ir.registerIcon(mod.assets + "board");
		icon_cover = ir.registerIcon(mod.assets + "cable_cover");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return blockIcon; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	public int isProvidingWeakPower(IBlockAccess iba, int x, int y, int z, int s)
	{
		TileCBCable t = (TileCBCable)iba.getTileEntity(x, y, z);
		return (t != null && t.isOutputtingRS(MathHelperLM.getDir(s).getOpposite().ordinal())) ? 15 : 0;
	}
	
	public boolean canConnectRedstone(IBlockAccess iba, int x, int y, int z, int side)
	{
		TileCBCable t = (TileCBCable)iba.getTileEntity(x, y, z);
		return (t != null && t.getBoard(side) != null);
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileCBCable)
		{
			if(((TileCBCable)te).hasCover)
			{
				return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
			}
			
			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
			
			if(mop != null && mop.subHit >= 0 && mop.subHit < boxes.length)
			{
				AxisAlignedBB aabb = boxes[mop.subHit].copy();
				aabb.minX += x; aabb.maxX += x;
				aabb.minY += y; aabb.maxY += y;
				aabb.minZ += z; aabb.maxZ += z;
				return aabb;
			}
		}
		
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
	}
	
	public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileCBCable)
		{
			if(((TileCBCable)te).hasCover)
			{
				return super.collisionRayTrace(w, x, y, z, start, end);
			}
			
			AxisAlignedBB[] boxes1 = boxes.clone();
			
			for(int i = 0; i < boxes.length; i++)
			{
				if(!((TileCBCable)te).isAABBEnabled(i))
					boxes1[i] = null;
			}
			
			return MathHelperLM.collisionRayTrace(w, x, y, z, start, end, boxes1);
		}
		
		return null;
	}
}