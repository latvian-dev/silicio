package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.gui.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSString extends ModuleConfigSegment
{
	public String defaultString = "";
	
	public ModuleCSString(int i, String s)
	{ super(i, s); }

	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{ g.mc.displayGuiScreen(new GuiCSText(g, this)); }
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{ set(cb.items[MID], data.getString("V")); cb.cable.markDirty(); }
	
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
	
	@SideOnly(Side.CLIENT)
	public void addButtonDesc(GuiModuleSettings g, FastList<String> s)
	{ String s1 = get(g.board.items[g.moduleID]); if(!s1.isEmpty()) s.add(s1); }
	
	public boolean isValid(String s)
	{ return true; }
}