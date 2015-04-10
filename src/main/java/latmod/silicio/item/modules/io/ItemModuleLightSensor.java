package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleLightSensor extends ItemModuleIO implements ISignalProvider
{
	public ItemModuleLightSensor(String s)
	{ super(s); }
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.OUTPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "S", "M",
				'S', Blocks.daylight_detector,
				'M', SilItems.Modules.EMPTY);
	}
	
	public void provideSignals(ItemStack is, CircuitBoard t)
	{
		if(t.cable.isServer() && t.cable.getWorldObj().getBlockLightValue(t.cable.xCoord + t.side.offsetX, t.cable.yCoord + t.side.offsetY, t.cable.zCoord + t.side.offsetZ) >= 9)
			getChannel(is, t, 0).enable();
	}
}