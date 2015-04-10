package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.item.modules.ICBModule;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

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
	public static final TextureCoords icon_cfg_side = new TextureCoords(thisTex, 21 * 4, 62);
	
	public CircuitBoard board;
	public ICBModule module;
	public int moduleID;
	
	public ButtonLM buttonChannels;
	public ButtonLM buttonBack;
	
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
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		if(module.getChannelCount() > 0)
			buttonChannels.render(icon_channels);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		FastList<String> al = new FastList<String>();
		
		if(buttonChannels.mouseOver(mx, my))
			al.add("Select Channels");
		
		if(buttonBack.mouseOver(mx, my))
			al.add(LC.mod.translate("back"));
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}