package latmod.silicio.item.modules.io;

import ftb.lib.item.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.item.modules.IOType;
import latmod.silicio.tile.cb.events.EventUpdateModule;
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
	
	public void onUpdate(EventUpdateModule e)
	{ if(e.isEnabled(0, -1, false)) e.board.redstoneOut = true; }
}