package latmod.silicio.tile;

import latmod.lib.IntList;
import latmod.silicio.SilBlocks;
import latmod.silicio.block.BlockLaserIO;
import latmod.silicio.block.BlockLaserMirrorBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public class TileLaserTX extends TileCBNetwork
{
	public byte refreshTick = 0;
	public final List<BlockPos> laserPath;
	public BlockPos receiver;
	
	public TileLaserTX()
	{
		laserPath = new ArrayList<>();
		receiver = null;
	}
	
	@Override
	public EnumSync getSync()
	{ return (laserPath.size() > 0) ? EnumSync.RERENDER : EnumSync.SYNC; }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		refreshTick = tag.getByte("Tick");
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setByte("Tick", refreshTick);
	}
	
	@Override
	public void readTileClientData(NBTTagCompound tag)
	{
		laserPath.clear();
		
		if(tag.hasKey("L"))
		{
			IntList list = new IntList();
			list.addAll(tag.getIntArray("L"));
			
			for(int i = 0; i < list.size(); i += 3)
			{
				laserPath.add(new BlockPos(list.get(i), list.get(i + 1), list.get(i + 2)));
			}
		}
	}
	
	@Override
	public void writeTileClientData(NBTTagCompound tag)
	{
		if(!laserPath.isEmpty())
		{
			IntList list = new IntList();
			
			for(BlockPos p : laserPath)
			{
				list.add(p.getX());
				list.add(p.getY());
				list.add(p.getZ());
			}
			
			tag.setIntArray("L", list.toArray());
		}
	}
	
	@Override
	public void onUpdate()
	{
		if(getSide().isServer())
		{
			refreshTick++;
			
			if(refreshTick > 10)
			{
				updateLaserPath();
				refreshTick = 0;
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(BlockPos pos)
	{
		super.onNeighborBlockChange(pos);
	}
	
	public void updateLaserPath()
	{
		laserPath.clear();
		
		if(getSide().isServer())
		{
			updateBlockState();
			getLaserPath(new BlockPos(0, 0, 0), currentState.getValue(BlockLaserIO.FACING));
			markDirty();
		}
	}
	
	private void getLaserPath(BlockPos pos, EnumFacing facing)
	{
		for(int i = 1; i < 64; i++)
		{
			BlockPos pos1 = pos.offset(facing, i);
			
			IBlockState state = worldObj.getBlockState(getPos().add(pos1));
			Block block = state.getBlock();
			
			if(block == SilBlocks.LASER_MIRROR)
			{
				if(!laserPath.contains(pos1))
				{
					laserPath.add(pos1);
					getLaserPath(pos1, state.getValue(BlockLaserMirrorBox.FACING));
				}
			}
			else if(block == SilBlocks.LASER_RX)
			{
				laserPath.add(pos1);
			}
			else if(block.isOpaqueCube(state))
			{
				return;
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{ return INFINITE_EXTENT_AABB; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{ return laserPath.isEmpty() ? 0D : (512D * 512D); }
}
