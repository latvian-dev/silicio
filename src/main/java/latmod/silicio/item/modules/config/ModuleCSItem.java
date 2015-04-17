package latmod.silicio.item.modules.config;

import latmod.core.util.FastList;
import latmod.silicio.gui.GuiModuleSettings;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSItem extends ModuleConfigSegment
{
	public ItemStack defaultItem = null;
	
	public ModuleCSItem(int i, String s)
	{ super(i, s); }

	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{
	}
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
	}
	
	public ItemStack get(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) set(is, defaultItem);
		
		NBTTagCompound tag1 = (NBTTagCompound)tag.getTag(SID);
		if(tag1.hasNoTags()) return null;
		return ItemStack.loadItemStackFromNBT(tag1);
	}
	
	public void set(ItemStack is, ItemStack item)
	{
		NBTTagCompound tag = data(is);
		NBTTagCompound tag1 = new NBTTagCompound();
		if(item != null) item.writeToNBT(tag1);
		tag.setTag(SID, tag1);
		setData(is, tag);
	}
	
	public void addButtonDesc(CircuitBoard cb, int MID, FastList<String> s)
	{ ItemStack item = get(cb.items[MID]); if(item != null) s.add(item.getDisplayName()); }
	
	public boolean isValid(ItemStack is)
	{ return true; }
}