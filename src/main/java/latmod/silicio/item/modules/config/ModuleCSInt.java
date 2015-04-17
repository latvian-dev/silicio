package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.gui.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.*;

public class ModuleCSInt extends ModuleConfigSegment
{
	public int defaultValue = 0;
	public int min = Integer.MIN_VALUE;
	public int max = Integer.MAX_VALUE;
	public boolean scrollGui = false;
	
	public ModuleCSInt(int i, String s)
	{ super(i, s); }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{ g.mc.displayGuiScreen(new GuiCSInt(g, this)); }
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{ set(cb.items[MID], data.getInteger("V")); cb.cable.markDirty(); }
	
	public int get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defaultValue);
		return MathHelper.clamp_int(tag.getInteger(SID), min, max);
	}
	
	public void set(ItemStack is, int i)
	{
		NBTTagCompound tag = data(is);
		tag.setInteger(SID, i);
		setData(is, tag);
	}
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ s.add("" + get(cb.items[MID])); }
	
	public boolean isValid(int i)
	{ return i >= min && i <= max; }
}