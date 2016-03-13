package latmod.silicio.tile;

import ftb.lib.api.item.LMInvUtils;
import latmod.silicio.api.modules.*;
import latmod.silicio.api.tileentity.IModuleSocketTile;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import java.util.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class TileModuleSocket extends TileCBNetwork implements IModuleSocketTile
{
	private final Map<EnumFacing, ModuleContainer> modules;
	
	public TileModuleSocket()
	{
		modules = new EnumMap<>(EnumFacing.class);
	}
	
	public EnumSync getSync()
	{ return EnumSync.RERENDER; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		modules.clear();
		
		NBTTagList list = tag.getTagList("Modules", Constants.NBT.TAG_COMPOUND);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tag1 = list.getCompoundTagAt(i);
			ModuleContainer c = ModuleContainer.readFromNBT(this, tag1);
			if(c != null)
			{
				modules.put(c.facing, c);
				c.module.init(c);
			}
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
		ModuleContainer c = modules.get(side);
		
		if(c != null)
		{
			if(getSide().isServer())
			{
				if(is == null && ep.isSneaking())
				{
					c.module.onRemoved(c, (EntityPlayerMP) ep);
					LMInvUtils.giveItem(ep, c.item.copy(), ep.inventory.currentItem);
					modules.remove(side);
					markDirty();
					if(getController() != null) getController().onCBNetworkChanged(getPos());
				}
			}
			
			return true;
		}
		else if(is != null && is.getItem() instanceof IModuleItem)
		{
			if(getSide().isServer())
			{
				Module m = ModuleRegistry.getFromStack(is);
				
				if(m != null)
				{
					c = new ModuleContainer(this, side, LMInvUtils.singleCopy(is), m);
					modules.put(c.facing, c);
					is.stackSize--;
					c.module.init(c);
					c.module.onAdded(c, (EntityPlayerMP) ep);
					markDirty();
					if(getController() != null) getController().onCBNetworkChanged(getPos());
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public void onUpdate()
	{
	}
	
	public boolean canCBConnect(EnumFacing facing)
	{ return !hasModule(facing); }
	
	public boolean hasModule(EnumFacing facing)
	{ return modules.containsKey(facing); }
	
	public Collection<ModuleContainer> getModules()
	{ return modules.values(); }
}