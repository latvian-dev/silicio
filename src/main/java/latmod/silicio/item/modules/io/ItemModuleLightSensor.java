package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSNum;
import latmod.silicio.item.modules.events.EventProvideSignals;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemModuleLightSensor extends ItemModuleIO implements ISignalProvider
{
	public static final ModuleCSNum cs_light = new ModuleCSNum(0, "Light Level");
	
	public ItemModuleLightSensor(String s)
	{
		super(s);
		cs_light.setValues(9, 1, 15, 1);
		moduleConfig.add(cs_light);
	}
	
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
	
	public void provideSignals(EventProvideSignals e)
	{
		if(e.cable.getWorldObj().getBlockLightValue(e.board.sidePos.posX, e.board.sidePos.posY, e.board.sidePos.posZ) >= cs_light.get(e.item()))
			e.setEnabled(0);
	}
}