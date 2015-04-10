package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.IOType;

public class ItemModuleGateNot extends ItemModuleLogic
{
	public ItemModuleGateNot(String s)
	{
		super(s);
	}
	
	public int getChannelCount()
	{ return 2; }
	
	public IOType getChannelType(int c)
	{ return c == 0 ? IOType.INPUT : IOType.OUTPUT; }
	
	public void loadRecipes()
	{
	}
}