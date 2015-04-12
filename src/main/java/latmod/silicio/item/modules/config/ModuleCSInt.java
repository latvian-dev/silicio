package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSInt extends ModuleConfigSegment
{
	public int min = Integer.MIN_VALUE;
	public int max = Integer.MAX_VALUE;
	public int value = 0;
	public boolean scrollGui = false;
	
	public ModuleCSInt(int i, String s)
	{ super(i, s); }
	
	@SideOnly(Side.CLIENT)
	public void buttonClicked(CircuitBoard cb, int MID, Minecraft mc)
	{
	}
	
	public boolean isValid(int i)
	{ return i >= min && i <= max; }
	
	public void readFromNBT(NBTTagCompound tag)
	{ value = tag.getInteger(SID); }
	
	public void writeToNBT(NBTTagCompound tag)
	{ tag.setInteger(SID, value); }
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ s.add("" + value); }
}