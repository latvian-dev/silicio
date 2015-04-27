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
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
	public void loadRecipes()
	{
	}
	
	public void onUpdate(ItemStack is, CircuitBoard t)
	{
	}
}