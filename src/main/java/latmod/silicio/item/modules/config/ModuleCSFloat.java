package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.*;

public class ModuleCSFloat extends ModuleConfigSegment
{
	public float defaultValue = 0;
	public float min = Float.MIN_VALUE;
	public float max = Float.MAX_VALUE;
	public boolean scrollGui = false;
	
	public ModuleCSFloat(int i, String s)
	{ super(i, s); }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(CircuitBoard cb, int MID, Minecraft mc)
	{
	}
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
	}
	
	public float get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defaultValue);
		return MathHelper.clamp_float(tag.getFloat(SID), min, max);
	}
	
	public void set(ItemStack is, float f)
	{
		NBTTagCompound tag = data(is);
		tag.setFloat(SID, f);
		setData(is, tag);
	}
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ s.add("" + get(cb.items[MID])); }
	
	public boolean isValid(float f)
	{ return f >= min && f <= max; }
}