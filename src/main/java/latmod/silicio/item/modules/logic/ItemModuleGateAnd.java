package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.IOType;

public class ItemModuleGateAnd extends ItemModuleLogic
{
	public ItemModuleGateAnd(String s)
	{
		super(s);
	}
	
	public int getChannelCount()
	{ return 5; }
	
	public IOType getChannelType(int c)
	{ return (c == 4) ? IOType.OUTPUT : IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
}