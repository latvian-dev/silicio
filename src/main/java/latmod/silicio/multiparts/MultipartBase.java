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
public abstract class MultipartBase extends Multipart implements IPartConverter.IPartConverter2
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
		block.addCollisionBoxesToList(getWorld(), getPos(), getWorld().getBlockState(getPos()), mask, list, e);
		
		/*
		List<AxisAlignedBB> list1 = new ArrayList<>();
		block.addCollisionBoxesToList(getWorld(), getPos(), getWorld().getBlockState(getPos()), mask, list1, e);
		
		if(list1.isEmpty()) return;
		BlockPos pos = getPos();
		
		for(AxisAlignedBB bb : list1)
		{
			list1.add(bb.offset(-pos.getX(), -pos.getY(), -pos.getZ()));
		}*/
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
	{ return new BlockState(MCMultiPartMod.multipart, block.getBlockState().getProperties().toArray(new IProperty[0])); }
	
	public Collection<Block> getConvertableBlocks()
	{ return Collections.singleton(block); }
	
	public Collection<? extends IMultipart> convertBlock(IBlockAccess w, BlockPos pos, boolean b)
	{
		IBlockState state = w.getBlockState(pos);
		
		if(state.getBlock() == block)
		{
			return Collections.singletonList(createNew());
		}
		
		return null;
	}
	
	public abstract MultipartBase createNew();
}
