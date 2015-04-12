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
	
	@SideOnly(Side.CLIENT)
	public abstract void buttonClicked(CircuitBoard cb, int MID, Minecraft mc);
	public abstract void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s);
	
	public final int hashCode()
	{ return ID; }
	
	public final boolean equals(Object o)
	{ return o != null && (o == this || o.hashCode() == hashCode()); }
}