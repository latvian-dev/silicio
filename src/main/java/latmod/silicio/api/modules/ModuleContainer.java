package latmod.silicio.api.modules;

import latmod.lib.IntMap;
import latmod.lib.LMUtils;
import latmod.silicio.api.tile.cb.ICBController;
import latmod.silicio.api.tile.cb.ICBModuleProvider;
import latmod.silicio.tile.TileModuleSocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public final class ModuleContainer
{
	public final ICBModuleProvider tile;
	public final EnumFacing facing;
	public final ItemStack item;
	public final Module module;
	public final IntMap connections;
	public NBTTagCompound data;
	public long tick;
	
	public ModuleContainer(ICBModuleProvider t, EnumFacing f, ItemStack is, Module m)
	{
		tile = LMUtils.nonNull(t);
		facing = f;
		item = LMUtils.nonNull(is);
		module = LMUtils.nonNull(m);
		connections = new IntMap();
	}
	
	public static ModuleContainer readFromNBT(TileModuleSocket tile, NBTTagCompound tag)
	{
		ItemStack item = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item"));
		
		if(item == null || !item.hasCapability(CapabilityModule.MODULE_CAPABILITY, null))
		{
			return null;
		}
		
		Module module = item.getCapability(CapabilityModule.MODULE_CAPABILITY, null);
		
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
	
	public void addConnection(ModuleIOConnection c)
	{ connections.put(c.index, 0); }
	
	public int getConnectionID(ModuleIOConnection c)
	{ return connections.get(c.index); }
	
	public boolean getSignalState(ICBController controller, ModuleIOConnection c)
	{
		if(c == null) { return false; }
		int id = getConnectionID(c);
		if(id != 0) { return false; }
		return controller.getSignalState(id);
	}
}
