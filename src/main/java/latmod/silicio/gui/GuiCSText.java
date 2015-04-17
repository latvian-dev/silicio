package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.silicio.item.modules.config.ModuleCSString;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCSText extends GuiModule
{
	public static final ResourceLocation thisTex = getTex("cs_text.png");
	
	public final GuiModuleSettings parent;
	public final ModuleCSString config;
	
	public ButtonLM buttonCancel;
	public ButtonLM buttonSave;
	public TextBoxLM textBox;
	
	public GuiCSText(GuiModuleSettings g, ModuleCSString c)
	{
		super(g.container.player, thisTex);
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
				if(config.isValid(textBox.text))
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("V", textBox.text);
					config.clientConfig(parent.board, parent.moduleID, tag);
					
					playClickSound();
					mc.displayGuiScreen(new GuiModuleSettings(parent));
				}
			}
		});
		
		widgets.add(textBox = new TextBoxLM(this, 8, 9, 159, 16)
		{
			public String getText()
			{ return (config.isValid(text) ? EnumChatFormatting.WHITE : EnumChatFormatting.RED) + text; }
		});
		
		textBox.text = c.get(parent.board.items[parent.moduleID]);
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