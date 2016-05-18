package latmod.silicio.api.modules;

import latmod.lib.LMUtils;
import latmod.silicio.api.SignalChannel;
import latmod.silicio.api.SilCapabilities;
import latmod.silicio.tile.TileModuleSocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public final class ModuleContainer
{
	public final TileEntity tile;
	public final EnumFacing facing;
	public final ItemStack item;
	public final Module module;
	public final Map<EnumModuleIO, ModuleConnection> connections;
	public NBTTagCompound data;
	public long tick;
	
	public ModuleContainer(TileEntity t, EnumFacing f, ItemStack is, Module m)
	{
		tile = LMUtils.nonNull(t);
		facing = f;
		item = LMUtils.nonNull(is);
		module = LMUtils.nonNull(m);
		connections = new HashMap<>();
	}
	
	public static ModuleContainer readFromNBT(TileModuleSocket tile, NBTTagCompound tag)
	{
		ItemStack item = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item"));
		
		if(item == null || !item.hasCapability(SilCapabilities.MODULE, null))
		{
			return null;
		}
		
		Module module = item.getCapability(SilCapabilities.MODULE, null);
		
		if(module == null)
		{
			return null;
		}
		
		EnumFacing facing = EnumFacing.VALUES[tag.getByte("Side")];
		
		ModuleContainer c = new ModuleContainer(tile, facing, item, module);
		c.tick = tag.getLong("Tick");
		c.data = tag.hasKey("Data") ? tag.getCompoundTag("Data") : null;
		return c;
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setByte("Side", (byte) facing.ordinal());
		
		NBTTagCompound tag2 = new NBTTagCompound();
		item.writeToNBT(tag2);
		tag.setTag("Item", tag2);
		tag.setLong("Tick", tick);
		
		if(data != null && !data.hasNoTags())
		{
			tag.setTag("Data", data);
		}
	}
	
	public void onUpdate()
	{
		module.onUpdate(this);
		tick++;
	}
	
	public void addConnection(EnumModuleIO e)
	{
		connections.put(e, new ModuleConnection(e));
	}
	
	public SignalChannel getChannel(EnumModuleIO e)
	{
		return connections.containsKey(e) ? connections.get(e).getChannel() : SignalChannel.NULL;
	}
}