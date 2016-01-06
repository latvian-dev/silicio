package latmod.silicio.item.modules.config;

import cpw.mods.fml.relauncher.*;
import ftb.lib.item.LMInvUtils;
import ftb.lib.mod.FTBLibMod;
import latmod.silicio.gui.GuiModuleSettings;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ModuleCSFluid extends ModuleConfigSegment
{
	public FluidStack defaultFluid = null;
	
	public ModuleCSFluid(int i, String s)
	{ super(i, s); }

	@SideOnly(Side.CLIENT)
	public void buttonClicked(GuiModuleSettings g)
	{
		ItemStack is = LMInvUtils.singleCopy(g.container.player.inventory.getItemStack());
		
		if(is == null && !FTBLibMod.proxy.isShiftDown()) return;
		
		FluidStack fluidFromItem = LMInvUtils.getFluid(is);
		if(is != null && fluidFromItem == null) return;
		
		FluidStack fs = (is == null) ? null : fluidFromItem;
		
		if(fs != null) fs.amount = 1000;
		
		if(isValid(fs))
		{
			NBTTagCompound tag = new NBTTagCompound();
			if(fs != null) fs.writeToNBT(tag);
			clientConfig(g.board, g.moduleID, tag);
			g.buttonClicked.setFluid(fs);
		}
	}
	
	public void onConfigReceived(CircuitBoard cb, int MID, NBTTagCompound data)
	{
		FluidStack is = data.hasNoTags() ? null : FluidStack.loadFluidStackFromNBT(data);
		setFluid(cb.items[MID], is);
		cb.cable.markDirty();
	}
	
	public FluidStack getFluid(ItemStack is)
	{
		NBTTagCompound tag = data(is);
		if(!tag.hasKey(SID)) setFluid(is, defaultFluid);
		NBTTagCompound tagI = tag.getCompoundTag(SID);
		if(tagI.hasNoTags()) return null;
		return FluidStack.loadFluidStackFromNBT(tagI);
	}
	
	public void setFluid(ItemStack is, FluidStack item)
	{
		NBTTagCompound tag = data(is);
		NBTTagCompound tagI = new NBTTagCompound();
		if(item != null) item.writeToNBT(tagI);
		tag.setTag(SID, tagI);
		setData(is, tag);
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void addButtonDesc(GuiModuleSettings g, List<String> s)
	{
		FluidStack fluid = getFluid(g.board.items[g.moduleID]);
		if(fluid != null) s.add(EnumChatFormatting.GRAY + fluid.getLocalizedName());
	}
	
	public boolean isValid(FluidStack is)
	{ return true; }
}