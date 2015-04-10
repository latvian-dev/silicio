package latmod.silicio.gui;
import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.Silicio;
import latmod.silicio.gui.container.ContainerCircuitBoard;
import latmod.silicio.tile.CircuitBoard;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCircuitBoard extends GuiLM
{
	public ButtonLM buttonSettings;
	public CircuitBoard board;
	
	public GuiCircuitBoard(ContainerCircuitBoard c)
	{
		super(c, Silicio.mod.getLocation("textures/gui/circuitBoard.png"));
		ySize = 138;
		
		board = (CircuitBoard)c.inv;
		
		widgets.add(buttonSettings = new ButtonLM(this, 146, 21, 16, 16)
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
		
		buttonSettings.render(Icons.settings);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		FastList<String> al = new FastList<String>();
		
		if(buttonSettings.mouseOver(mx, my))
			al.add(LC.mod.translate("settings"));
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}