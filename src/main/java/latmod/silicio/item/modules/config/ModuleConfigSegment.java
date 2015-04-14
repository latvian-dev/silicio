package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public abstract class ModuleConfigSegment
{
	public final int ID;
	public final String SID;
	public final String title;
	
	public ModuleConfigSegment(int i, String s)
	{ ID = i; SID = "" + ID; title = s; }
	
	public NBTTagCompound data(ItemStack is)
	{
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		return is.stackTagCompound.getCompoundTag("Config");
	}
	
	public void setData(ItemStack is, NBTTagCompound tag)
	{
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setTag("Config", tag);
	}
	
	@SideOnly(Side.CLIENT)
	public abstract void buttonClicked(CircuitBoard cb, int MID, Minecraft mc);
	public abstract void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data);
	public abstract void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s);
	
	public final void clientConfig(CircuitBoard cb, int MID, NBTTagCompound tag)
	{ cb.cable.clientModuleConfig(cb, MID, ID, tag); }
	
	public final int hashCode()
	{ return ID; }
	
	public final boolean equals(Object o)
	{ return o != null && (o == this || o.hashCode() == hashCode()); }
}