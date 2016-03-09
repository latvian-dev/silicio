package latmod.silicio.tile;

import latmod.lib.IntList;
import latmod.silicio.SilItems;
import latmod.silicio.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public class TileLaserTX extends TileCBNetwork
{
	public byte refreshTick = 0;
	public final List<BlockPos> laserPath;
	
	public TileLaserTX()
	{
		laserPath = new ArrayList<>();
	}
	
	public boolean rerenderBlock()
	{ return laserPath.size() > 0; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		refreshTick = tag.getByte("Tick");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setByte("Tick", refreshTick);
	}
	
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
	
	public void onUpdate()
	{
		if(isServer())
		{
			refreshTick++;
			
			if(refreshTick > 20)
			{
				updateLaserPath();
				refreshTick = 0;
			}
		}
	}
	
	public void onNeighborBlockChange(BlockPos pos)
	{
		super.onNeighborBlockChange(pos);
	}
	
	public void updateLaserPath()
	{
		laserPath.clear();
		
		if(isServer())
		{
			getLaserPath(getPos(), currentState.getValue(BlockLaserIO.FACING));
			markDirty();
		}
	}
	
	private void getLaserPath(BlockPos pos, EnumFacing facing)
	{
		for(int i = 1; i < 64; i++)
		{
			BlockPos pos1 = pos.offset(facing, i);
			
			IBlockState state = worldObj.getBlockState(pos1);
			
			if(state.getBlock() == SilItems.b_laser_mirror)
			{
				laserPath.add(pos1);
				getLaserPath(pos1, state.getValue(BlockLaserMirrorBox.FACING));
			}
			else if(state.getBlock() == SilItems.b_laser_rx)
			{
				laserPath.add(pos1);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{ return INFINITE_EXTENT_AABB; }
}
