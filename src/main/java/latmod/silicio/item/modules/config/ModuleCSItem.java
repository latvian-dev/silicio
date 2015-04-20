package latmod.silicio.item.modules.config;

import latmod.core.InvUtils;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.gui.GuiModuleSettings;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.*;

public class ModuleCSItem extends ModuleConfigSegment
{
	public ItemStack defaultItem = null;
	
	public ModuleCSItem(int i, String s)
	{ super(i, s); }

	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{
		ItemStack is = InvUtils.singleCopy(g.container.player.inventory.getItemStack());
		
		if(is == null && !LC.proxy.isShiftDown()) return;
		
		if(isValid(is))
		{
			NBTTagCompound tag = new NBTTagCompound();
			if(is != null) is.writeToNBT(tag);
			clientConfig(g.board, g.moduleID, tag);
			g.buttonClicked.background = (is == null) ? GuiModuleSettings.icon_cfg_item : is;
		}
	}
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
		ItemStack is = data.hasNoTags() ? null : ItemStack.loadItemStackFromNBT(data);
		setItem(cb.items[MID], is);
		cb.cable.markDirty();
	}
	
	public ItemStack getItem(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) setItem(is, defaultItem);
		NBTTagCompound tagI = tag.getCompoundTag(SID);
		if(tagI.hasNoTags()) return null;
		return ItemStack.loadItemStackFromNBT(tagI);
	}
	
	public void setItem(ItemStack is, ItemStack item)
	{
		NBTTagCompound tag = data(is);
		NBTTagCompound tagI = new NBTTagCompound();
		if(item != null) item.writeToNBT(tagI);
		tag.setTag(SID, tagI);
		setData(is, tag);
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void addButtonDesc(GuiModuleSettings g, FastList<String> s)
	{
		ItemStack item = getItem(g.board.items[g.moduleID]);
		if(item != null)
		{
			s.add(EnumChatFormatting.GRAY + item.getDisplayName());
			item.getItem().addInformation(item, g.container.player, s, false);
		}
	}
	
	public boolean isValid(ItemStack is)
	{ return true; }
}