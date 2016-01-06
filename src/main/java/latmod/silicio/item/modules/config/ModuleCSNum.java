package latmod.silicio.item.modules.config;

import cpw.mods.fml.relauncher.*;
import latmod.silicio.gui.*;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ModuleCSNum extends ModuleConfigSegment
{
	public int defValue, minValue, maxValue, scrollValue;
	
	public ModuleCSNum(int i, String s)
	{
		super(i, s);
		setValues(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
	}
	
	public void setDefValue(int def)
	{ defValue = def; }
	
	public void setValues(int def, int min, int max, int scroll)
	{ defValue = def; minValue = min; maxValue = max; scrollValue = scroll; }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{ g.mc.displayGuiScreen(new GuiCSNum(g, this)); }
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{ set(cb.items[MID], data.getInteger("V")); cb.cable.markDirty(); }
	
	public int get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defValue);
		return MathHelper.clamp_int(tag.getInteger(SID), minValue, maxValue);
	}
	
	public void set(ItemStack is, int i)
	{
		NBTTagCompound tag = data(is);
		tag.setInteger(SID, i);
		setData(is, tag);
	}
	
	@SideOnly(Side.CLIENT)
	public void addButtonDesc(GuiModuleSettings g, List<String> s)
	{ s.add("" + get(g.board.items[g.moduleID])); }
	
	public boolean isValid(int i)
	{ return i >= minValue && i <= maxValue; }
}