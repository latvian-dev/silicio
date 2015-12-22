package latmod.silicio.gui;
import java.util.List;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.gui.*;
import ftb.lib.gui.GuiLM;
import ftb.lib.gui.widgets.ButtonLM;
import latmod.silicio.Silicio;
import latmod.silicio.gui.container.ContainerCircuitBoard;
import latmod.silicio.tile.cb.*;
import net.minecraft.util.EnumChatFormatting;

@SideOnly(Side.CLIENT)
public class GuiCircuitBoard extends GuiLM
{
	public final ButtonLM buttonSettings;
	public CircuitBoard board;
	
	public GuiCircuitBoard(ContainerCircuitBoard c)
	{
		super(c, Silicio.mod.getLocation("textures/gui/circuitBoard.png"));
		ySize = 138;
		
		board = (CircuitBoard)c.inv;
		
		buttonSettings = new ButtonLM(this, 146, 21, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				if(board.cable.getCBNetwork().controller != null)
				{
					playClickSound();
					board.cable.clientOpenGui(TileCBCable.guiData(board.side, 1, -1));
				}
			}
			
			public void addMouseOverText(List<String> l)
			{
				l.add(title);
				
				if(board.cable.getCBNetwork().controller == null)
					l.add(EnumChatFormatting.GRAY + "No Controller!");
			}
		};
		
		buttonSettings.title = FTBLibLang.button_settings();
	}
	
	public void addWidgets()
	{
		mainPanel.add(buttonSettings);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		buttonSettings.render(GuiIcons.settings);
	}
}