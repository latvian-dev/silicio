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
	
	public void provideSignals(CircuitBoard cb, int MID)
	{
		if(cb.cable.isServer() && cb.cable.getWorldObj().getBlockLightValue(cb.cable.xCoord + cb.side.offsetX, cb.cable.yCoord + cb.side.offsetY, cb.cable.zCoord + cb.side.offsetZ) >= 9)
			getChannel(cb, MID, 0).enable();
	}
}