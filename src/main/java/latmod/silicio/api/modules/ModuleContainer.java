package latmod.silicio.api.modules;

import latmod.lib.LMUtils;
import latmod.silicio.api.tileentity.IModuleSocketTile;
import latmod.silicio.tile.TileModuleSocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleContainer
{
	public final IModuleSocketTile tile;
	public final EnumFacing facing;
	public final ItemStack item;
	public final IModule module;
	public final Map<Integer, ModuleChannel> channelMap;
	public long tick;
	
	public final boolean needsUpdate;
	
	public ModuleContainer(IModuleSocketTile t, EnumFacing f, ItemStack is, IModule m)
	{
		tile = LMUtils.nonNull(t);
		facing = LMUtils.nonNull(f);
		item = LMUtils.nonNull(is);
		module = LMUtils.nonNull(m);
		channelMap = new HashMap<>();
		
		needsUpdate = m instanceof ITickingModule;
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound tag1 = new NBTTagCompound();
		tag1.setByte("Side", (byte) facing.ordinal());
		
		NBTTagCompound tag2 = new NBTTagCompound();
		item.writeToNBT(tag2);
		tag1.setTag("Item", tag2);
	}
	
	public static ModuleContainer readFromNBT(TileModuleSocket tile, NBTTagCompound tag)
	{
		EnumFacing facing = EnumFacing.VALUES[tag.getByte("Side")];
		ItemStack item = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item"));
		IModule module = ModuleRegistry.getFromStack(item);
		
		if(module != null)
		{
			return new ModuleContainer(tile, facing, item, module);
		}
		
		return null;
	}
	
	public void onUpdate()
	{
		if(needsUpdate)
		{
			((ITickingModule) module).onUpdate(this);
			tick++;
		}
	}
	
	public void addChannel(ModuleChannel c)
	{ channelMap.put(c.ordinal, c); }
	
	public boolean getState(ModuleChannel c)
	{
		if(c.type == ModuleChannel.Type.INPUT)
		{
		}
		
		return false;
	}
	
	public void setState(ModuleChannel c, boolean state)
	{
		if(c.type == ModuleChannel.Type.OUTPUT)
		{
		}
	}
}
