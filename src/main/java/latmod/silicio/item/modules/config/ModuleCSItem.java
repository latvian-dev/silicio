package latmod.silicio.item.modules.config;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSItem extends ModuleConfigSegment<ItemStack>
{
	@SideOnly(Side.CLIENT)
	public void openGui(Minecraft mc)
	{
	}
	
	public void setData(NBTTagCompound data, int ID, ItemStack t)
	{
		if(t != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			t.writeToNBT(tag);
			data.setTag("" + ID, tag);
		}
	}
	
	public ItemStack getData(NBTTagCompound data, int ID)
	{
		NBTTagCompound tag = (NBTTagCompound)data.getTag("" + ID);
		return (tag == null) ? null : ItemStack.loadItemStackFromNBT(tag);
	}
}