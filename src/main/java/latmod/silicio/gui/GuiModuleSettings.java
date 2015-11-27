package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.gui.FTBLibLang;
import ftb.lib.client.*;
import ftb.lib.gui.GuiLM;
import ftb.lib.gui.widgets.ButtonLM;
import latmod.lib.FastList;
import latmod.silicio.gui.container.ContainerModuleSettings;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.cb.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;

@SideOnly(Side.CLIENT)
public class GuiModuleSettings extends GuiLM
{
	public static final ResourceLocation thisTex = GuiModule.getTex("moduleSettings.png");
	public static final TextureCoords icon_channels = new TextureCoords(thisTex, 176, 0, 21, 21);
	public static final TextureCoords icon_cfg_empty = new TextureCoords(thisTex, 197, 0, 21, 21);
	public static final TextureCoords icon_cfg_text = new TextureCoords(thisTex, 218, 0, 21, 21);
	public static final TextureCoords icon_cfg_num = new TextureCoords(thisTex, 176, 21, 21, 21);
	public static final TextureCoords icon_cfg_bool = new TextureCoords(thisTex, 197, 21, 21, 21);
	public static final TextureCoords icon_cfg_item = new TextureCoords(thisTex, 218, 21, 21, 21);
	public static final TextureCoords icon_cfg_fluid = new TextureCoords(thisTex, 218, 42, 21, 21);
	
	public final CircuitBoard board;
	public final ItemModule module;
	public final int moduleID;
	
	public final ButtonLM buttonChannels;
	public final ButtonLM buttonBack;
	public final ConfigButton[] buttonsConfig;
	public ConfigButton buttonClicked;
	
	private static RenderItem renderItem = new RenderItem();
	
	public GuiModuleSettings(ContainerModuleSettings c, int id)
	{
		super(c, thisTex);
		xSize = 176;
		ySize = 142;
		board = (CircuitBoard)c.inv;
		moduleID = id;
		module = board.getModule(moduleID);
		
		buttonChannels = new ButtonLM(this, 8, 9, 21, 21)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				board.cable.clientOpenGui(TileCBCable.guiData(board.side, 3, moduleID));
			}
		};
		
		buttonChannels.title = "Select Channels";
		
		buttonBack = new ButtonLM(this, 8, 32, 21, 21)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				board.cable.clientOpenGui(TileCBCable.guiData(board.side, 1, -1));
			}
		};
		
		buttonBack.title = FTBLibLang.button_back();
		
		buttonsConfig = new ConfigButton[12];
		
		for(final ModuleConfigSegment mcs : module.moduleConfig)
		{
			if(mcs == null || mcs.ID < 0 || mcs.ID >= buttonsConfig.length) continue;
			
			buttonsConfig[mcs.ID] = new ConfigButton(this, 31 + 23 * (mcs.ID % 6), 9 + 23 * (mcs.ID / 6), 21, 21)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					buttonClicked = this;
					mcs.buttonClicked(GuiModuleSettings.this);
				}
				
				public void addMouseOverText(FastList<String> l)
				{
					l.add(mcs.title);
					FastList<String> l1 = new FastList<String>();
					mcs.addButtonDesc(GuiModuleSettings.this, l1);
					for(String s : l1) l.add(EnumChatFormatting.DARK_GRAY + s);
				}
			};
			
			if(mcs instanceof ModuleCSBool) buttonsConfig[mcs.ID].background = icon_cfg_bool;
			else if(mcs instanceof ModuleCSNum) buttonsConfig[mcs.ID].background = icon_cfg_num;
			else if(mcs instanceof ModuleCSItem)
			{
				buttonsConfig[mcs.ID].background = icon_cfg_item;
				ItemStack is = ((ModuleCSItem)mcs).getItem(board.items[moduleID]);
				if(is != null) buttonsConfig[mcs.ID].itemStack = is;
			}
			else if(mcs instanceof ModuleCSFluid)
			{
				buttonsConfig[mcs.ID].background = icon_cfg_fluid;
				FluidStack fs = ((ModuleCSFluid)mcs).getFluid(board.items[moduleID]);
				if(fs != null) buttonsConfig[mcs.ID].fluidStack = fs;
			}
			else buttonsConfig[mcs.ID].background = icon_cfg_text;
		}
	}
	
	public GuiModuleSettings(GuiModuleSettings parent)
	{ this(new ContainerModuleSettings(parent.container.player, parent.board), parent.moduleID); }
	
	public void addWidgets()
	{
		mainPanel.add(buttonBack);
		if(module.getChannelCount() > 0)
			mainPanel.add(buttonChannels);
		mainPanel.addAll(buttonsConfig);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		if(module.getChannelCount() > 0)
			buttonChannels.render(icon_channels);
		
		for(ConfigButton b : buttonsConfig)
		{
			if(b == null) continue;
			
			if(b.itemStack != null)
			{
				b.render(icon_cfg_empty);
				FTBLibClient.renderGuiItem(b.itemStack, renderItem, mc.fontRenderer, b.getAX(), b.getAY());
			}
			else if(b.fluidStack != null)
			{
				b.render(icon_cfg_empty);
				Fluid fl = b.fluidStack.getFluid();
				
				IIcon ic = fl.getStillIcon();
				if(ic == null && fl.getBlock() != null)
					ic = fl.getBlock().getBlockTextureFromSide(1);
				
				if(ic != null)
				{
					GlStateManager.pushMatrix();
					setTexture(TextureMap.locationBlocksTexture);
					GlStateManager.translate(b.posX + guiLeft + 2.5F, b.posY + guiTop + 2.5F, 0F);
					drawWrappedIcon(ic, 0, 0, 16, 16);
					setTexture(thisTex);
					GlStateManager.popMatrix();
				}
			}
			else if(b.background != null) b.render(b.background);
		}
	}
	
	public static class ConfigButton extends ButtonLM
	{
		public ItemStack itemStack = null;
		public FluidStack fluidStack = null;
		
		public ConfigButton(GuiLM g, int x, int y, int w, int h)
		{
			super(g, x, y, w, h);
		}
		
		public void setItem(ItemStack is)
		{
			background = icon_cfg_item;
			itemStack = is;
		}
		
		public void setFluid(FluidStack fs)
		{
			background = icon_cfg_fluid;
			fluidStack = fs;
		}
		
		public void onButtonPressed(int b)
		{
		}
	}
}