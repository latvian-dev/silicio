package latmod.silicio.tile;

import ftb.lib.LMNBTUtils;
import ftb.lib.api.item.LMInvUtils;
import latmod.silicio.api.modules.*;
import latmod.silicio.api.tileentity.IModuleSocketTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;

import java.util.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class TileModuleSocket extends TileCBNetwork implements IModuleSocketTile
{
	public final Map<EnumFacing, ModuleContainer> modules;
	
	public TileModuleSocket()
	{
		modules = new EnumMap<>(EnumFacing.class);
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		modules.clear();
		
		NBTTagList list = tag.getTagList("Modules", LMNBTUtils.MAP);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tag1 = list.getCompoundTagAt(i);
			ModuleContainer c = ModuleContainer.readFromNBT(this, tag1);
			if(c != null) modules.put(c.facing, c);
		}
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		
		NBTTagList list = new NBTTagList();
		
		for(ModuleContainer m : modules.values())
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			m.writeToNBT(tag1);
			list.appendTag(tag1);
		}
		
		tag.setTag("Modules", list);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, EnumFacing side, float x, float y, float z)
	{
		if(is == null && ep.isSneaking())
		{
			if(modules.containsKey(side))
			{
				LMInvUtils.giveItem(ep, modules.get(side).item.copy(), ep.inventory.currentItem);
				modules.remove(side);
				markDirty();
			}
			
			return true;
		}
		
		if(is != null && is.getItem() instanceof IModuleItem)
		{
			if(!modules.containsKey(side))
			{
				IModule module = ModuleRegistry.getFromStack(is);
				
				if(module != null)
				{
					ModuleContainer m = new ModuleContainer(this, side, LMInvUtils.singleCopy(is), module);
					modules.put(m.facing, m);
					is.stackSize--;
					markDirty();
				}
			}
			
			return true;
		}
		
		return true;
	}
	
	public void onUpdate()
	{
		for(ModuleContainer c : modules.values())
		{
			c.onUpdate();
		}
	}
	
	public boolean canCBConnect(EnumFacing facing)
	{
		return !modules.containsKey(facing);
	}
	
	public ModuleContainer getModule(EnumFacing facing)
	{ return modules.get(facing); }
}