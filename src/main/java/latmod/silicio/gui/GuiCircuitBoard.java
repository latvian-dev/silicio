package latmod.silicio.gui;
import latmod.ftbu.FTBU;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.silicio.Silicio;
import latmod.silicio.gui.container.ContainerCircuitBoard;
import latmod.silicio.tile.cb.*;
import net.minecraft.util.EnumChatFormatting;
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
				if(board.cable.controller != null)
				{
					playClickSound();
					board.cable.clientOpenGui(TileCBCable.guiData(board.side, 1, -1));
				}
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(title);
				
				if(board.cable.controller == null)
					l.add(EnumChatFormatting.GRAY + "No Controller!");
			}
		});
		
		buttonSettings.title = FTBU.mod.translate("button.settings");
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		buttonSettings.render(Icons.settings);
	}
}