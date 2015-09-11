package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.client.FTBULang;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.*;
import latmod.silicio.item.modules.config.ModuleCSNum;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCSNum extends GuiModule
{
	public static final ResourceLocation thisTex = getTex("cs_num.png");
	
	public final GuiModuleSettings parent;
	public final ModuleCSNum config;
	
	public final ButtonLM buttonCancel, buttonSave;
	public final TextBoxLM textBox;
	
	public final ButtonLM buttonsInc[] = new ButtonLM[4];
	public final int inc[] = { -100, -1, 1, 100 };
	public final int incX[] = { 8, 25, 100, 117 };
	
	public GuiCSNum(GuiModuleSettings g, ModuleCSNum c)
	{
		super(g.container.player, thisTex);
		parent = g;
		config = c;
		
		xSize = 141;
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
		};
		
		buttonSave.title = FTBULang.button_save;
		
		for(int bi = 0; bi < buttonsInc.length; bi++)
		{
			final int bf = bi;
			
			buttonsInc[bf] = new ButtonLM(this, incX[bf], 9, 16, 16)
			{
				public void onButtonPressed(int b)
				{
					Integer i = Converter.toInt(textBox.text);
					
					if(i != null && config.isValid(i))
					{
						i += inc[bf] * (isShiftKeyDown() ? 10 : 1);
						i = MathHelperLM.clampInt(i, config.minValue, config.maxValue);
						textBox.text = "" + i;
					}
				}
				
				public void addMouseOverText(FastList<String> l)
				{ l.add((inc[bf] < 0 ? "" : "+") + (inc[bf] * (isShiftKeyDown() ? 10 : 1))); }
			};
		}
		
		textBox = new TextBoxLM(this, 42, 9, 57, 16)
		{
			public String getText()
			{
				Integer i = Converter.toInt(textBox.text);
				return ((i != null && config.isValid(i.intValue())) ? EnumChatFormatting.WHITE : EnumChatFormatting.RED) + text;
			}
		};
	}
	
	public void addWidgets()
	{
		textBox.text = "" + config.get(parent.board.items[parent.moduleID]);
		
		mainPanel.add(buttonCancel);
		mainPanel.add(buttonSave);
		mainPanel.addAll(buttonsInc);
		mainPanel.add(textBox);
	}
	
	public void drawText(FastList<String> l)
	{
		textBox.renderCentred(textBox.posX + textBox.width / 2, 13, 0xFFFFFFFF);
		super.drawText(l);
	}
}