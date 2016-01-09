package latmod.silicio.item.modules.config;

import cpw.mods.fml.relauncher.*;
import latmod.silicio.gui.GuiModuleSettings;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ModuleCSBool extends ModuleConfigSegment
{
	public boolean defaultValue = false;
	
	public ModuleCSBool(int i, String s)
	{ super(i, s); }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{ clientConfig(g.board, g.moduleID, null); }
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
		set(cb.items[MID], !get(cb.items[MID]));
		cb.cable.markDirty();
	}
	
	public boolean get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defaultValue);
		return tag.getBoolean(SID);
	}
	
	public void set(ItemStack is, boolean b)
	{
		NBTTagCompound tag = data(is);
		tag.setBoolean(SID, b);
		setData(is, tag);
	}
	
	@SideOnly(Side.CLIENT)
	public void addButtonDesc(GuiModuleSettings g, List<String> s)
	{ s.add(get(g.board.items[g.moduleID]) ? "True" : "False"); }
}