package latmod.silicio.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.core.util.*;
import latmod.silicio.client.render.world.RenderCBCable;
import latmod.silicio.tile.TileCBCable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
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
	private static final FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
	
	static { updateBoxes(); }
	
	private static void updateBoxes()
	{
		boxes.clear();
		
		double d = pipeBorder;
		
		addBB(d, d, d, 1D - d, 1D - d, 1D - d);
		
		//addBB(f, 0D, f, 1D - f, h, 1D - f);
		addBB(d, 0D, d, 1D - d, d, 1D - d);
		
		//addBB(f, 1D - h, f, 1D - f, 1D, 1D - f);
		addBB(d, 1D - d, d, 1D - d, 1D, 1D - d);
		
		//addBB(f, f, 0D, 1D - f, 1D - f, h);
		addBB(d, d, 0D, 1D - d, 1D - d, d);
		
		//addBB(f, f, 1D - h, 1D - f, 1D - f, 1D);
		addBB(d, d, 1D - d, 1D - d, 1D - d, 1D);
		
		//addBB(0D, f, f, h, 1D - f, 1D - f);
		addBB(0D, d, d, d, 1D - d, 1D - d);
		
		//addBB(1D - h, f, f, 1D, 1D - f, 1D - f);
		addBB(1D - d, d, d, 1D, 1D - d, 1D - d);
	}
	
	private static void addBB(double x1, double y1, double z1, double x2, double y2, double z2)
	{ boxes.add(AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2)); }
	
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
		mod.recipes.addRecipe(new ItemStack(this, 32), "RRR", "SES", "RRR",
				'R', ODItems.RUBBER,
				'E', ODItems.SILVER,
				'S', ODItems.REDSTONE);
		
		mod.recipes.addRecipe(new ItemStack(this, 32), "RRR", "SES", "RRR",
				'R', ODItems.SLIMEBALL,
				'E', ODItems.SILVER,
				'S', ODItems.REDSTONE);
	}
	
	public void setBlockBoundsForItemRender()
	{
		float s = pipeBorder;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
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
			
			boolean x0 = TileCBCable.connectCable(t, ForgeDirection.WEST);
			boolean x1 = TileCBCable.connectCable(t, ForgeDirection.EAST);
			boolean y0 = TileCBCable.connectCable(t, ForgeDirection.DOWN);
			boolean y1 = TileCBCable.connectCable(t, ForgeDirection.UP);
			boolean z0 = TileCBCable.connectCable(t, ForgeDirection.NORTH);
			boolean z1 = TileCBCable.connectCable(t, ForgeDirection.SOUTH);
			
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
			
			updateBoxes();
			
			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
			
			if(mop != null && mop.subHit >= 0 && mop.subHit < boxes.size())
			{
				AxisAlignedBB aabb = boxes.get(mop.subHit).copy();
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
			
			updateBoxes();
			
			for(int i = 0; i < boxes.size(); i++)
			{
				if(!((TileCBCable)te).isAABBEnabled(i))
					boxes.set(i, null);
			}
			
			return MathHelperLM.collisionRayTrace(w, x, y, z, start, end, boxes);
		}
		
		return null;
	}
}