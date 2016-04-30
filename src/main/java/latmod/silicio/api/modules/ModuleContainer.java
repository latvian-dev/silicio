package latmod.silicio.api.modules;

import latmod.lib.IntMap;
import latmod.lib.LMUtils;
import latmod.silicio.api.tile.ICBController;
import latmod.silicio.api.tile.IModuleSocketTile;
import latmod.silicio.tile.TileModuleSocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public final class ModuleContainer
{
	public final IModuleSocketTile tile;
	public final EnumFacing facing;
	public final ItemStack item;
	public final Module module;
	public final IntMap connections;
	public NBTTagCompound data;
	public long tick;
	
	public ModuleContainer(IModuleSocketTile t, EnumFacing f, ItemStack is, Module m)
	{
		tile = LMUtils.nonNull(t);
		facing = f;
		item = LMUtils.nonNull(is);
		module = LMUtils.nonNull(m);
		connections = new IntMap();
	}
	
	public static ModuleContainer readFromNBT(TileModuleSocket tile, NBTTagCompound tag)
	{
		EnumFacing facing = EnumFacing.VALUES[tag.getByte("Side")];
		Module module = ModuleRegistry.get(tag.getString("ID"));
		
		if(module != null)
		{
			ItemStack item = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item"));
			ModuleContainer c = new ModuleContainer(tile, facing, item, module);
			c.tick = tag.getLong("Tick");
			c.data = tag.hasKey("Data") ? tag.getCompoundTag("Data") : null;
			return c;
		}
		
		return null;
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setByte("Side", (byte) facing.ordinal());
		tag.setString("ID", module.getID());
		
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
	
	public boolean getSignalState(ModuleIOConnection c)
	{
		if(c == null) return false;
		int id = getConnectionID(c);
		if(id != 0) return false;
		ICBController controller = tile.getController();
		return controller != null && controller.getSignalState(id);
	}
}
