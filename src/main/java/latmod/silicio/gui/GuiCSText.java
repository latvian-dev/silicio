package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.client.FTBULang;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.silicio.item.modules.config.ModuleCSString;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCSText extends GuiModule
{
	public static final ResourceLocation thisTex = getTex("cs_text.png");
	
	public final GuiModuleSettings parent;
	public final ModuleCSString config;
	
	public final ButtonLM buttonCancel, buttonSave;
	public final TextBoxLM textBox;
	
	public GuiCSText(GuiModuleSettings g, ModuleCSString c)
	{
		super(g.container.player, thisTex);
		parent = g;
		config = c;
		
		xSize = 176;
		ySize = 54;
		
		buttonCancel = new ButtonLM(this, 8, 29, 78, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				mc.displayGuiScreen(parent);
			}
		};
		
		buttonCancel.title = FTBULang.button_cancel;
		
		buttonSave = new ButtonLM(this, 89, 29, 78, 16)
		{
			public void onButtonPressed(int b)
			{
				if(config.isValid(textBox.text))
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("V", textBox.text);
					config.clientConfig(parent.board, parent.moduleID, tag);
					
					playClickSound();
					mc.displayGuiScreen(new GuiModuleSettings(parent));
				}
			}
		};
		
		buttonSave.title = FTBULang.button_save;
		
		textBox = new TextBoxLM(this, 8, 9, 159, 16)
		{
			public String getText()
			{ return (config.isValid(text) ? EnumChatFormatting.WHITE : EnumChatFormatting.RED) + text; }
		};
		
		textBox.text = c.get(parent.board.items[parent.moduleID]);
	}
	
	public void addWidgets()
	{
		mainPanel.add(buttonCancel);
		mainPanel.add(buttonSave);
		mainPanel.add(textBox);
	}
	
	public void drawText(FastList<String> l)
	{
		textBox.render(12, 13, 0xFFFFFFFF);
		super.drawText(l);
	}
}