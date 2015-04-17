package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.util.Converter;
import latmod.silicio.item.modules.config.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCSFloat extends GuiModule
{
	public final GuiModuleSettings parent;
	public final ModuleCSFloat config;
	
	public ButtonLM buttonCancel;
	public ButtonLM buttonSave;
	public TextBoxLM textBox;
	
	public GuiCSFloat(GuiModuleSettings g, ModuleCSFloat c)
	{
		super(g.container.player, GuiCSText.thisTex);
		parent = g;
		config = c;
		
		xSize = 176;
		ySize = 54;
		
		widgets.add(buttonCancel = new ButtonLM(this, 8, 29, 78, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				mc.displayGuiScreen(parent);
			}
		});
		
		widgets.add(buttonSave = new ButtonLM(this, 89, 29, 78, 16)
		{
			public void onButtonPressed(int b)
			{
				Float f = Converter.toFloat(textBox.text);
				
				if(f != null && config.isValid(f.floatValue()))
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setFloat("V", f.floatValue());
					config.clientConfig(parent.board, parent.moduleID, tag);
					
					playClickSound();
					mc.displayGuiScreen(new GuiModuleSettings(parent));
				}
			}
		});
		
		widgets.add(textBox = new TextBoxLM(this, 8, 9, 159, 16)
		{
			public String getText()
			{
				Float f = Converter.toFloat(textBox.text);
				return ((f != null && config.isValid(f.floatValue())) ? EnumChatFormatting.WHITE : EnumChatFormatting.RED) + text;
			}
		});
		
		textBox.text = "" + c.get(parent.board.items[parent.moduleID]);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		textBox.render(12, 13, 0xFFFFFFFF);
	}
}