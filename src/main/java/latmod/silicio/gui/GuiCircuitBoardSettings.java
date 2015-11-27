package latmod.silicio.gui;
import cpw.mods.fml.relauncher.*;
import ftb.lib.api.gui.*;
import ftb.lib.gui.GuiLM;
import ftb.lib.gui.widgets.*;
import latmod.silicio.Silicio;
import latmod.silicio.gui.container.ContainerCircuitBoardSettings;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.tile.cb.*;

@SideOnly(Side.CLIENT)
public class GuiCircuitBoardSettings extends GuiLM
{
	public final ButtonLM buttonBack;
	public final CircuitBoard board;
	public final ItemButtonLM itemButtons[];
	
	public GuiCircuitBoardSettings(ContainerCircuitBoardSettings c)
	{
		super(c, Silicio.mod.getLocation("textures/gui/circuitBoardSettings.png"));
		ySize = 138;
		
		board = (CircuitBoard)c.inv;
		
		buttonBack = new ButtonLM(this, 146, 21, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				board.cable.clientOpenGui(TileCBCable.guiData(board.side, 0, -1));
			}
		};
		
		buttonBack.title = FTBLibLang.button_back();
		
		itemButtons = new ItemButtonLM[board.items.length];
		
		for(int y = 0; y < 2; y++)
		for(int x = 0; x < 6; x++)
		{
			final int id = x + y * 6;
			
			if(board.items[id] != null && board.items[id].getItem() instanceof ItemModule)
			{
				itemButtons[id] = new ItemButtonLM(this, 9 + x * 22, 10 + y * 22, 18, 18)
				{
					public void onButtonPressed(int b)
					{
						playClickSound();
						board.cable.clientOpenGui(TileCBCable.guiData(board.side, 2, id));
					}
				};
				
				itemButtons[id].setItem(board.items[id]);
				itemButtons[id].title = itemButtons[id].item.getDisplayName();
				//itemButtons[id].setBackground(button_pressed);
			}
		}
	}
	
	public void addWidgets()
	{
		mainPanel.add(buttonBack);
		mainPanel.addAll(itemButtons);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		buttonBack.render(GuiIcons.back);
		
		for(int i = 0; i < itemButtons.length; i++)
		{
			if(itemButtons[i] != null)
				itemButtons[i].renderWidget();
		}
	}
}