package latmod.silicio.item.modules.config;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.util.FastList;
import latmod.ftbu.mod.FTBU;
import latmod.silicio.gui.GuiModuleSettings;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class ModuleCSMode extends ModuleConfigSegment
{
	public String[] modes = { "Default" };
	public String[] desc = null;
	public int defaultValue = 0;
	
	public ModuleCSMode(int i, String s)
	{ super(i, s); }
	
	public void setModes(int def, String... m)
	{ defaultValue = def; modes = m; }
	
	public void setDesc(int mode, String d)
	{ if(desc == null) desc = new String[modes.length]; desc[mode] = d; }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{ clientConfig(g.board, g.moduleID, null); }
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
		set(cb.items[MID], (get(cb.items[MID]) + 1) % modes.length);
		cb.cable.markDirty();
	}
	
	public int get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defaultValue);
		int i = tag.getByte(SID);
		return MathHelper.clamp_int(i, 0, modes.length);
	}
	
	public void set(ItemStack is, int b)
	{
		NBTTagCompound tag = data(is);
		tag.setByte(SID, (byte)b);
		setData(is, tag);
	}
	
	@SideOnly(Side.CLIENT)
	public void addButtonDesc(GuiModuleSettings g, FastList<String> s)
	{
		int mode = get(g.board.items[g.moduleID]);
		if(FTBU.proxy.isShiftDown())
		{
			for(int i = 0; i < modes.length; i++)
			{
				if(i != mode) s.add(modes[i]);
				else s.add(EnumChatFormatting.GREEN + modes[i]);
			}
		}
		else
		{
			s.add(modes[mode]);
			if(desc != null && desc[mode] != null)
				s.add(desc[mode]);
		}
	}
}