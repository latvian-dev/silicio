package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.gui.container.ContainerModuleSettings;
import latmod.silicio.item.modules.ICBModule;
import latmod.silicio.item.modules.config.*;
import latmod.silicio.tile.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiModuleSettings extends GuiLM
{
	public static final ResourceLocation thisTex = GuiModule.getTex("moduleSettings.png");
	public static final TextureCoords icon_channels = new TextureCoords(thisTex, 176, 0);
	public static final TextureCoords icon_cfg_text = new TextureCoords(thisTex, 218, 0);
	public static final TextureCoords icon_cfg_num = new TextureCoords(thisTex, 176, 21);
	public static final TextureCoords icon_cfg_bool = new TextureCoords(thisTex, 197, 21);
	public static final TextureCoords icon_cfg_item = new TextureCoords(thisTex, 218, 21);
	
	public final CircuitBoard board;
	public final ICBModule module;
	public final int moduleID;
	
	public ButtonLM buttonChannels;
	public ButtonLM buttonBack;
	
	public ButtonLM buttonClicked;
	
	public FastList<ButtonLM> buttonsConfig;
	
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
				board.cable.clientOpenGui(TileCBCable.guiData(board.side.ordinal(), 3, moduleID));
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
				board.cable.clientOpenGui(TileCBCable.guiData(board.side.ordinal(), 1, -1));
			}
		});
		
		buttonBack.title = LC.mod.translate("button.back");
		
		buttonsConfig = new FastList<ButtonLM>();
		
		for(final ModuleConfigSegment mcs : module.getModuleConfig())
		{
			ButtonLM b = new ButtonLM(this, 31 + 23 * (mcs.ID % 6), 9 + 23 * (mcs.ID / 6), 21, 21)
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
			
			if(mcs instanceof ModuleCSBool) b.background = icon_cfg_bool;
			else if(mcs instanceof ModuleCSInt || mcs instanceof ModuleCSFloat) b.background = icon_cfg_num;
			else if(mcs instanceof ModuleCSItem)
			{
				b.background = icon_cfg_item;
				ItemStack is = ((ModuleCSItem)mcs).getItem(board.items[moduleID]);
				if(is != null) b.background = is;
			}
			else b.background = icon_cfg_text;
			
			buttonsConfig.add(b);
			widgets.add(b);
		}
	}
	
	public GuiModuleSettings(GuiModuleSettings parent)
	{ this(new ContainerModuleSettings(parent.container.player, parent.board), parent.moduleID); }
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		if(module.getChannelCount() > 0)
			buttonChannels.render(icon_channels);
		
		for(ButtonLM b : buttonsConfig)
		{
			if(b.background instanceof ItemStack)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(b.posX + guiLeft + 2.5F, b.posY + guiTop + 2.5F, 0F);
				GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glEnable(GL11.GL_LIGHTING);
				renderItem.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), (ItemStack)b.background, 0, 0, false);
				renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), (ItemStack)b.background, 0, 0);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glPopAttrib();
				GL11.glPopMatrix();
			}
			else b.render(b.background);
		}
	}
}