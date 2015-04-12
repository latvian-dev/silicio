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
		
		if(t.cable.isServer() && te != null && te instanceof TileEntitySign)
		{
			TileEntitySign tes = (TileEntitySign)te;
			
			String[] s =
			{
				"",
				"Test",
				"",
				"",
			};
			
			for(int i = 0; i < 4; i++)
				tes.signText[i] = s[i];
			
			tes.markDirty();
			tes.getWorldObj().markBlockForUpdate(tes.xCoord, tes.yCoord, tes.zCoord);
		}
	}
}