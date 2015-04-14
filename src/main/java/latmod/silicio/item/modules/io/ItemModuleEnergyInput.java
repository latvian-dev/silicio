package latmod.silicio.item.modules.io;

import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;

public class ItemModuleEnergyInput extends ItemModuleIO
{
	public ItemModuleEnergyInput(String s)
	{
		super(s);
		setTextureName("energy");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
	
	public boolean canReceive(CircuitBoard cb, int MID)
	{
		CBChannel c = getChannel(cb, MID, 0);
		return c == CBChannel.NONE || c.isEnabled();
	}
	
	public void onUpdate(ItemStack is, CircuitBoard t)
	{
	}
}