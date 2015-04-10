package latmod.silicio.item.modules.io;

import latmod.core.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.CircuitBoard;
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
	
	public void provideSignals(ItemStack is, CircuitBoard t)
	{
		if(t.cable.getWorldObj().isBlockProvidingPowerTo(t.cable.xCoord + t.side.offsetX, t.cable.yCoord + t.side.offsetY, t.cable.zCoord + t.side.offsetZ, t.side.ordinal()) > 0)
			getChannel(is, t, 0).enable();
	}
}