package latmod.silicio.api.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Created by LatvianModder on 04.05.2016.
 */
public class LinkedPos
{
	public final BlockPos pos;
	public final EnumFacing facing;
	
	public LinkedPos(BlockPos p, EnumFacing f)
	{
		pos = p;
		facing = f;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null) { return false; }
		else if(o == this) { return true; }
		else if(o instanceof LinkedPos)
		{
			LinkedPos p = (LinkedPos) o;
			return p.facing == facing && p.pos.equals(pos);
		}
		return false;
	}
	
	public IBlockState getBlockState(IBlockAccess w)
	{ return w.getBlockState(pos.offset(facing)); }
	
	public TileEntity getTile(IBlockAccess w)
	{ return w.getTileEntity(pos.offset(facing)); }
}