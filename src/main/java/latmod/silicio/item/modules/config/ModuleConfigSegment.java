package latmod.silicio.item.modules.config;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public abstract class ModuleConfigSegment<T>
{
	@SideOnly(Side.CLIENT)
	public abstract void openGui(Minecraft mc);
	
	public abstract void setData(NBTTagCompound data, int ID, T t);
	public abstract T getData(NBTTagCompound data, int ID);
	
	public boolean isValid(T o)
	{ return true; }
}