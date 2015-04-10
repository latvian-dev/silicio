package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;

public class ItemModuleCommandBlock extends ItemModuleLogic
{
	public ItemModuleCommandBlock(String s)
	{ super(s); }
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
	
	public void onUpdate(ItemStack is, CircuitBoard t)
	{
		if(is.hasTagCompound())
		{
			//String cmd = is.stackTagCompound.getString("Cmd");
		}
	}
}