package latmod.silicio.item.modules.io;

import latmod.core.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.item.ItemStack;

public class ItemModuleRedstoneOut extends ItemModuleIO
{
	public ItemModuleRedstoneOut(String s)
	{
		super(s);
		setTextureName("redstone");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.OUTPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "R", "M",
				'R', ODItems.REDSTONE,
				'M', SilItems.Modules.OUTPUT);
	}
	
	public void onUpdate(CircuitBoard cb, int MID)
	{
		if(cb.cable.isServer() && getChannel(cb, MID, 0).isEnabled())
			cb.redstoneOut = true;
	}
}