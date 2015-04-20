package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.silicio.tile.TileCBController;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiController extends GuiLM
{
	public static final ResourceLocation thisTex = GuiModule.getTex("controller.png");
	public static final TextureCoords iconChannelEnabled = new TextureCoords(thisTex, 166, 0);
	
	public TileCBController controller;
	public ButtonLM[] channels;
	
	public GuiController(ContainerEmpty c)
	{
		super(c, thisTex);
		
		xSize = 166;
		ySize = 85;
		controller = (TileCBController)c.inv;
		
		channels = new ButtonLM[controller.channels.length];
		
		for(int i = 0; i < controller.channels.length; i++)
		{
			int bx = i % 16;
			int by = i / 16;
			
			channels[i] = new ButtonLM(this, 16 + bx * 9, 7 + by * 9, 8, 8)
			{
				public void onButtonPressed(int b)
				{
				}
			};
			
			channels[i].customID = i;
			channels[i].title = controller.channels[i].name;
			
			widgets.add(channels[i]);
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		for(int i = 0; i < channels.length; i++)
		{
			if(controller.channels[i].isEnabled())
				iconChannelEnabled.render(this, channels[i].posX, channels[i].posY, 8, 8);
		}
	}
}