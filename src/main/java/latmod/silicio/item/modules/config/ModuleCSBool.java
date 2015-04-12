package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSBool extends ModuleConfigSegment
{
	public boolean defaultValue = false;
	
	public ModuleCSBool(int i, String s)
	{ super(i, s); }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(CircuitBoard cb, int MID, Minecraft mc)
	{
	}
	
	public boolean get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID))
			tag.setBoolean(SID, defaultValue);
		return tag.getBoolean(SID);
	}
	
	public void set(ItemStack is, boolean b)
	{ data(is).setBoolean(SID, b); }
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ s.add(get(cb.items[MID]) ? "True" : "False"); }
}