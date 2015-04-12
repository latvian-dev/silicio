package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSFloat extends ModuleConfigSegment
{
	public float min = Float.NEGATIVE_INFINITY;
	public float max = Float.POSITIVE_INFINITY;
	public float value = 0F;
	public boolean scrollGui = false;
	
	public ModuleCSFloat(int i, String s)
	{ super(i, s); }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(CircuitBoard cb, int MID, Minecraft mc)
	{
	}
	
	public boolean isValid(float i)
	{ return i >= min && i <= max; }
	
	public void readFromNBT(NBTTagCompound tag)
	{ value = tag.getFloat(SID); }
	
	public void writeToNBT(NBTTagCompound tag)
	{ tag.setFloat(SID, value); }
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ s.add(value + ""); }
}