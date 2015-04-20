package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.util.Converter;
import latmod.silicio.item.modules.config.ModuleCSInt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCSInt extends GuiModule
{
	public final GuiModuleSettings parent;
	public final ModuleCSInt config;
	
	public ButtonLM buttonCancel;
	public ButtonLM buttonSave;
	public TextBoxLM textBox;
	
	public GuiCSInt(GuiModuleSettings g, ModuleCSInt c)
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
				Integer i = Converter.toInt(textBox.text);
				
				if(i != null && config.isValid(i.intValue()))
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("V", i.intValue());
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
				Integer i = Converter.toInt(textBox.text);
				return ((i != null && config.isValid(i.intValue())) ? EnumChatFormatting.WHITE : EnumChatFormatting.RED) + text;
			}
		});
		
		textBox.text = "" + c.get(parent.board.items[parent.moduleID]);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		textBox.render(12, 13, 0xFFFFFFFF);
	}
}