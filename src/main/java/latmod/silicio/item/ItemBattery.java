package latmod.silicio.item;

import latmod.core.util.FastList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.EnergyHelper;
import cpw.mods.fml.relauncher.*;

public class ItemBattery extends ItemSil implements IEnergyContainerItem
{
	public static final int SEND = 4000;
	public static final int RECEIVE = 8000;
	public static final int CAPACITY = 4000000;
	
	public ItemBattery(String s)
	{
		super(s);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}
	
	public int receiveEnergy(ItemStack is, int e, boolean simulate)
	{
		if(is.stackTagCompound == null) EnergyHelper.setDefaultEnergyTag(is, 0);
		int i = is.stackTagCompound.getInteger("Energy");
		int j = Math.min(e, Math.min(CAPACITY - i, RECEIVE));
		if (!simulate) { i += j; is.stackTagCompound.setInteger("Energy", i); }
		return j;
	}
	
	public int extractEnergy(ItemStack is, int e, boolean simulate)
	{
		if(is.stackTagCompound == null) EnergyHelper.setDefaultEnergyTag(is, 0);
		int i = is.stackTagCompound.getInteger("Energy");
		int j = Math.min(e, Math.min(i, SEND));
		if (!simulate) { i -= j; is.stackTagCompound.setInteger("Energy", i); }
		return j;
	}
	
	public int getEnergyStored(ItemStack is)
	{
		if(is.stackTagCompound == null) EnergyHelper.setDefaultEnergyTag(is, 0);
		return is.stackTagCompound.getInteger("Energy");
	}
	
	public int getMaxEnergyStored(ItemStack is)
	{ return CAPACITY; }
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> s)
	{
	}
}