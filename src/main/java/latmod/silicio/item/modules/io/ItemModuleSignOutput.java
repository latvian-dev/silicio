package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;

public class ItemModuleSignOutput extends ItemModuleIO implements IToggable
{
	public ItemModuleSignOutput(String s)
	{
		super(s);
		setTextureName("sign");
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.OUTPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "S", "M",
				'M', SilItems.Modules.OUTPUT,
				'S', Items.sign);
	}
	
	public void onChannelToggled(ItemStack is, CircuitBoard t, CBChannel c)
	{
		if(!c.isEnabled() || c != getChannel(is, t, 0)) return;
		
		TileEntity te = t.getFacingTile();
		
		if(te != null && te instanceof TileEntitySign)
		{
			int line = 1;
			String text = "Hello!";
			
			((TileEntitySign)te).signText[line] = text;
			te.markDirty();
		}
	}
}