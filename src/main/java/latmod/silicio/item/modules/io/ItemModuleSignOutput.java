package latmod.silicio.item.modules.io;

import latmod.silicio.SilItems;
import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSString;
import latmod.silicio.tile.cb.events.EventChannelToggled;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;

public class ItemModuleSignOutput extends ItemModuleIO implements IToggable
{
	public static final ModuleCSString[] cs_text = new ModuleCSString[4];
	
	public ItemModuleSignOutput(String s)
	{
		super(s);
		setTextureName("sign");
		
		for(int i = 0; i < 4; i++)
		{
			cs_text[i] = new ModuleCSString(i, "Line #" + (i + 1))
			{
				public boolean isValid(String s)
				{ return s.length() < 20; }
			};
			
			cs_text[i].defaultString = "~";
			moduleConfig.add(cs_text[i]);
		}
		
		channelNames[0] = "Input";
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getModuleType()
	{ return IOType.OUTPUT; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "S", "M", 'M', SilItems.Modules.OUTPUT, 'S', Items.sign);
	}
	
	public void onChannelToggled(EventChannelToggled e)
	{
		if(e.isEnabled(0, e.channel, false))
		{
			TileEntity te = e.board.getFacingTile();
			
			if(e.board.cable.isServer() && te != null && te instanceof TileEntitySign)
			{
				TileEntitySign tes = (TileEntitySign) te;
				
				for(int i = 0; i < 4; i++)
				{
					String s = cs_text[i].get(e.item());
					if(!s.equals("~")) tes.signText[i] = s;
				}
				
				tes.markDirty();
				tes.getWorldObj().markBlockForUpdate(tes.xCoord, tes.yCoord, tes.zCoord);
			}
		}
	}
}