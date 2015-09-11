package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.gui.*;
import latmod.silicio.tile.cb.TileCBController;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiController extends GuiLM
{
	public static final ResourceLocation thisTex = GuiModule.getTex("controller.png");
	public static final TextureCoords iconChannelEnabled = new TextureCoords(thisTex, 166, 0, 8, 8);
	
	public final TileCBController controller;
	public final WidgetLM[] channels;
	
	public GuiController(ContainerEmpty c)
	{
		super(c, thisTex);
		
		xSize = 166;
		ySize = 85;
		controller = (TileCBController)c.inv;
		
		channels = new ButtonLM[TileCBController.MAX_CHANNEL];
		
		for(int i = 0; i < channels.length; i++)
		{
			int bx = i % 16;
			int by = i / 16;
			
			channels[i] = new WidgetLM(this, 16 + bx * 9, 7 + by * 9, 8, 8);
			channels[i].title = "[" + (i + 1) + "] " + TileCBController.getChannelName(i);
		}
	}
	
	public void addWidgets()
	{
		mainPanel.addAll(channels);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		for(int i = 0; i < channels.length; i++)
		{
			if(controller.channels.contains(i))
				iconChannelEnabled.render(this, channels[i].posX, channels[i].posY, 8, 8);
		}
	}
}