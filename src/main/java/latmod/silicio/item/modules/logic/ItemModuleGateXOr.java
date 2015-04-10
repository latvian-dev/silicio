package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.IOType;

public class ItemModuleGateXOr extends ItemModuleLogic
{
	public ItemModuleGateXOr(String s)
	{
		super(s);
	}
	
	public int getChannelCount()
	{ return 5; }
	
	public IOType getChannelType(int c)
	{ return c == 4 ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
}