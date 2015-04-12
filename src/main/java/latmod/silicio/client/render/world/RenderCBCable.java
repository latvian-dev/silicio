package latmod.silicio.client.render.world;
import latmod.core.client.BlockRendererLM;
import latmod.core.tile.IPaintable;
import latmod.silicio.SilItems;
import latmod.silicio.block.BlockCBCable;
import latmod.silicio.tile.TileCBCable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderCBCable extends BlockRendererLM
{
	public static final RenderCBCable instance = new RenderCBCable();
	
	public AxisAlignedBB cableBoxes[] = new AxisAlignedBB[6];
	public AxisAlignedBB boardBoxes[] = new AxisAlignedBB[6];
	
	public AxisAlignedBB cableFlatBoxes[] = new AxisAlignedBB[6];
	public AxisAlignedBB boardFlatBoxes[] = new AxisAlignedBB[6];
	
	public RenderCBCable()
	{
		updateBoxes();
	}
	
	public void updateBoxes()
	{
		{
			double s = BlockCBCable.pipeBorder;
			
			addBox(cableBoxes, ForgeDirection.WEST, 0D, s, s, s, 1D - s, 1D - s);
			addBox(cableBoxes, ForgeDirection.EAST, 1D - s, s, s, 1D, 1D - s, 1D - s);
			addBox(cableBoxes, ForgeDirection.DOWN, s, 0D, s, 1D - s, s, 1D - s);
			addBox(cableBoxes, ForgeDirection.UP, s, 1D - s, s, 1D - s, 1D, 1D - s);
			addBox(cableBoxes, ForgeDirection.NORTH, s, s, 0D, 1D - s, 1D - s, s);
			addBox(cableBoxes, ForgeDirection.SOUTH, s, s, 1D - s, 1D - s, 1D - s, 1D);
		}
		
		{
			double f = 1D / 16D * 4D;
			double h = 1D / 16D * 2D;
			
			addBox(boardBoxes, ForgeDirection.UP, f, 1D - h, f, 1D - f, 1D, 1D - f);
			addBox(boardBoxes, ForgeDirection.DOWN, f, 0D, f, 1D - f, h, 1D - f);
			addBox(boardBoxes, ForgeDirection.EAST, 1D - h, f, f, 1D, 1D - f, 1D - f);
			addBox(boardBoxes, ForgeDirection.WEST, 0D, f, f, h, 1D - f, 1D - f);
			addBox(boardBoxes, ForgeDirection.NORTH, f, f, 0D, 1D - f, 1D - f, h);
			addBox(boardBoxes, ForgeDirection.SOUTH, f, f, 1D - h, 1D - f, 1D - f, 1D);
		}
		
		double e = -0.005D;
		
		{
			double s = BlockCBCable.pipeBorder;
			
			addBox(cableFlatBoxes, ForgeDirection.WEST, e, s, s, e, 1D - s, 1D - s);
			addBox(cableFlatBoxes, ForgeDirection.EAST, 1D - e, s, s, 1D - e, 1D - s, 1D - s);
			addBox(cableFlatBoxes, ForgeDirection.DOWN, s, e, s, 1D - s, e, 1D - s);
			addBox(cableFlatBoxes, ForgeDirection.UP, s, 1D - e, s, 1D - s, 1D - e, 1D - s);
			addBox(cableFlatBoxes, ForgeDirection.NORTH, s, s, e, 1D - s, 1D - s, e);
			addBox(cableFlatBoxes, ForgeDirection.SOUTH, s, s, 1D - e, 1D - s, 1D - s, 1D - e);
		}
		
		{
			double f = 1D / 16D * 4D;
			
			addBox(boardFlatBoxes, ForgeDirection.UP, f, 1D - e, f, 1D - f, 1D - e, 1D - f);
			addBox(boardFlatBoxes, ForgeDirection.DOWN, f, e, f, 1D - f, e, 1D - f);
			addBox(boardFlatBoxes, ForgeDirection.EAST, 1D - e, f, f, 1D - e, 1D - f, 1D - f);
			addBox(boardFlatBoxes, ForgeDirection.WEST, e, f, f, e, 1D - f, 1D - f);
			addBox(boardFlatBoxes, ForgeDirection.NORTH, f, f, e, 1D - f, 1D - f, e);
			addBox(boardFlatBoxes, ForgeDirection.SOUTH, f, f, 1D - e, 1D - f, 1D - f, 1D - e);
		}
	}
	
	private void addBox(AxisAlignedBB[] b, ForgeDirection d, double x1, double y1, double z1, double x2, double y2, double z2)
	{ b[d.ordinal()] = AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2); }
	
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer0)
	{
		renderBlocks.setOverrideBlockTexture(SilItems.b_cbcable.getBlockIcon());
		
		double s = BlockCBCable.pipeBorder;
		renderBlocks.setRenderBounds(s, s, 0D, 1D - s, 1D - s, 1D);
		
		renderBlocks.renderBlockAsItem(block, metadata, 1F);
	}
	
	private boolean renderCables(TileCBCable t)
	{
		for(int i = 0; i < 6; i++)
		{
			if(t.paint[i] != null && t.paint[i].block != null)
				if(!t.paint[i].block.renderAsNormalBlock() || !t.paint[i].block.isOpaqueCube())
					return true;
		}
		
		return false;
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block block, int modelId, RenderBlocks renderer0)
	{
		//updateBoxes();
		
		renderBlocks.blockAccess = iba;
		
		TileCBCable t = (TileCBCable)iba.getTileEntity(x, y, z);
		
		if(t.hasCover)
		{
			renderBlocks.renderAllFaces = false;
			renderBlocks.setRenderBounds(renderBlocks.fullBlock);
			
			double d0 = 0.0001D; double d1 = 1D - d0;
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(d0, d0, d0, d1, d1, d1);
			
			IPaintable.Renderer.renderCube(iba, renderBlocks, t.paint, IPaintable.Renderer.to6(SilItems.b_cbcable.icon_cover), x, y, z, bb);
			
			//renderBlocks.renderAllFaces = true;
			
			if(!renderCables(t))
			{
				renderBlocks.setOverrideBlockTexture(SilItems.b_cbcable.getBlockIcon());
				
				for(int i = 0; i < 6; i++)
				{
					if(TileCBCable.connectCable(t, ForgeDirection.VALID_DIRECTIONS[i]))
					{
						renderBlocks.setRenderBounds(cableFlatBoxes[i]);
						renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
					}
				}
				
				renderBlocks.setOverrideBlockTexture(SilItems.b_cbcable.icon_board);
				
				for(int i = 0; i < 6; i++)
				{
					if(t.boards[i] != null)
					{
						renderBlocks.setRenderBounds(boardFlatBoxes[i]);
						renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
					}
				}
				
				return true;
			}
		}
		
		renderBlocks.renderAllFaces = true;
		
		renderBlocks.setOverrideBlockTexture(SilItems.b_cbcable.getBlockIcon());
		
		double s = BlockCBCable.pipeBorder;
		renderBlocks.setRenderBounds(s, s, s, 1F - s, 1F - s, 1F - s);
		renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		
		for(int i = 0; i < 6; i++)
		{
			if(TileCBCable.connectCable(t, ForgeDirection.VALID_DIRECTIONS[i]))
			{
				renderBlocks.setRenderBounds(cableBoxes[i]);
				renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
			}
		}
		
		renderBlocks.setOverrideBlockTexture(SilItems.b_cbcable.icon_board);
		
		for(int i = 0; i < 6; i++)
		{
			if(t.boards[i] != null)
			{
				renderBlocks.setRenderBounds(boardBoxes[i]);
				renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
			}
		}
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int modelId)
	{ return true; }
}