package latmod.silicio.multiparts;

import mcmultipart.MCMultiPartMod;
import mcmultipart.multipart.*;
import mcmultipart.raytrace.PartMOP;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

import java.util.*;

/**
 * Created by LatvianModder on 07.02.2016.
 */
public abstract class MultipartBase extends Multipart implements IPartConverter.IPartConverter2, IPartConverter.IReversePartConverter
{
	public final Block block;
	
	public MultipartBase(Block b)
	{
		block = b;
	}
	
	public void addSelectionBoxes(List<AxisAlignedBB> list)
	{
		BlockPos pos = getPos();
		list.add(block.getSelectedBoundingBox(getWorld(), pos).offset(-pos.getX(), -pos.getY(), -pos.getZ()));
	}
	
	public void addCollisionBoxes(AxisAlignedBB mask, List<AxisAlignedBB> list, Entity e)
	{
		BlockPos pos = getPos();
		
		block.addCollisionBoxesToList(getWorld(), pos, getWorld().getBlockState(pos), mask.offset(pos.getX(), pos.getY(), pos.getZ()), list, e);
		
		if(list.isEmpty()) return;
		
		for(int i = 0; i < list.size(); i++)
		{
			list.set(i, list.get(i).offset(-pos.getX(), -pos.getY(), -pos.getZ()));
		}
	}
	
	public ItemStack getPickBlock(EntityPlayer player, PartMOP hit)
	{
		return new ItemStack(block);
	}
	
	public List<ItemStack> getDrops()
	{
		return Collections.singletonList(new ItemStack(block));
	}
	
	public IBlockState getExtendedState(IBlockState state)
	{
		return block.getActualState(state, getWorld(), getPos());
	}
	
	public BlockState createBlockState()
	{
		Collection<IProperty> var = block.getBlockState().getProperties();
		return new BlockState(MCMultiPartMod.multipart, var.toArray(new IProperty[var.size()]));
	}
	
	public Collection<Block> getConvertableBlocks()
	{ return Collections.singleton(block); }
	
	public Collection<? extends IMultipart> convertBlock(IBlockAccess w, BlockPos pos, boolean b)
	{ return Collections.singletonList(createNew()); }
	
	public abstract MultipartBase createNew();
	
	public boolean convertToBlock(IMultipartContainer c)
	{
		if(c.getParts().size() == 1)
		{
			IMultipart m = c.getParts().iterator().next();
			if(m.getClass().equals(getClass())) return true;
		}
		return false;
	}
}
