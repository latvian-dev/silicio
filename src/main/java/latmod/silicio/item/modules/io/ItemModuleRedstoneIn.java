package latmod.silicio.item.modules.io;

import latmod.core.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.events.EventProvideSignals;
import net.minecraft.item.ItemStack;

public class ItemModuleRedstoneIn extends ItemModuleIO implements ISignalProvider
{
	public ItemModuleRedstoneIn(String s)
	{
		super(s);
		setTextureName("redstone");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.INPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "R", "M",
				'R', ODItems.REDSTONE,
				'M', SilItems.Modules.INPUT);
	}
	
	public void provideSignals(EventProvideSignals e)
	{
		if(e.cable.getWorldObj().isBlockProvidingPowerTo(e.board.sidePos.posX, e.board.sidePos.posY, e.board.sidePos.posZ, e.board.side) > 0)
			e.setEnabled(0);
	}
}