package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSString extends ModuleConfigSegment
{
	public String defaultString = "";
	
	public ModuleCSString(int i, String s)
	{ super(i, s); }

	@SideOnly(Side.CLIENT)
	public void buttonClicked(CircuitBoard cb, int MID, Minecraft mc)
	{
	}
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
	}
	
	public String get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defaultString);
		return tag.getString(SID);
	}
	
	public void set(ItemStack is, String s)
	{
		NBTTagCompound tag = data(is);
		tag.setString(SID, s);
		setData(is, tag);
	}
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ String s1 = get(cb.items[MID]); if(!s1.isEmpty()) s.add(s1); }
	
	public boolean isValid(String s)
	{ return true; }
}