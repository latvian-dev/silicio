package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.item.modules.ICBModule;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiModuleSettings extends GuiModule
{
	public static final ResourceLocation thisTex = getTex("moduleSettings.png");
	public static final TextureCoords icon_channels = new TextureCoords(thisTex, 176, 0);
	public static final TextureCoords icon_cfg_empty = new TextureCoords(thisTex, 21 * 0, 62);
	public static final TextureCoords icon_cfg_text = new TextureCoords(thisTex, 21 * 1, 62);
	public static final TextureCoords icon_cfg_num = new TextureCoords(thisTex, 21 * 2, 62);
	public static final TextureCoords icon_cfg_item = new TextureCoords(thisTex, 21 * 3, 62);
	public static final TextureCoords icon_cfg_bool = new TextureCoords(thisTex, 21 * 4, 62);
	
	public final CircuitBoard board;
	public final ICBModule module;
	public final int moduleID;
	
	public ButtonLM buttonChannels;
	public ButtonLM buttonBack;
	
	public FastList<ButtonLM> buttonsConfig;
	
	public GuiModuleSettings(EntityPlayer ep, CircuitBoard cb, ICBModule m, int id)
	{
		super(ep, thisTex);
		xSize = 176;
		ySize = 62;
		board = cb;
		module = m;
		moduleID = id;
		
		buttonChannels = new ButtonLM(this, 8, 9, 21, 21)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				mc.displayGuiScreen(new GuiSelectChannels(container.player, board, module, moduleID));
			}
		};
		
		buttonChannels.title = "Select Channels";
		
		if(module.getChannelCount() > 0)
			widgets.add(buttonChannels);
		
		widgets.add(buttonBack = new ButtonLM(this, 8, 32, 21, 21)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				board.cable.clientOpenGui(1);
			}
		});
		
		buttonBack.title = LC.mod.translate("button.back");
		
		buttonsConfig = new FastList<ButtonLM>();
		
		for(final ModuleConfigSegment mcs : m.getModuleConfig())
		{
			ButtonLM b = new ButtonLM(this, 31 + 23 * (mcs.ID % 6), 9 + 23 * (mcs.ID / 6), 21, 21)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					mcs.buttonClicked(GuiModuleSettings.this);
				}
				
				public void addMouseOverText(FastList<String> l)
				{
					l.add(mcs.title);
					FastList<String> l1 = new FastList<String>();
					mcs.addButtonDesc(board, moduleID, l1);
					for(String s : l1) l.add(EnumChatFormatting.DARK_GRAY + s);
				}
			};
			
			if(mcs instanceof ModuleCSBool) b.background = icon_cfg_bool;
			else if(mcs instanceof ModuleCSInt || mcs instanceof ModuleCSFloat) b.background = icon_cfg_num;
			else if(mcs instanceof ModuleCSItem) b.background = icon_cfg_item;
			else b.background = icon_cfg_text;
			
			buttonsConfig.add(b);
			widgets.add(b);
		}
	}
	
	public GuiModuleSettings(GuiModuleSettings parent)
	{ this(parent.container.player, parent.board, parent.board.getModule(parent.moduleID), parent.moduleID); }
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		if(module.getChannelCount() > 0)
			buttonChannels.render(icon_channels);
		
		for(ButtonLM b : buttonsConfig)
			b.render(b.background);
	}
}