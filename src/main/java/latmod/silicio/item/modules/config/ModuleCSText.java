package latmod.silicio.item.modules.config;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSText extends ModuleConfigSegment<String>
{
	@SideOnly(Side.CLIENT)
	public void openGui(Minecraft mc)
	{
	}
	
	public void setData(NBTTagCompound data, int ID, String t)
	{ data.setString("" + ID, t); }
	
	public String getData(NBTTagCompound data, int ID)
	{ return data.getString("" + ID); }
}