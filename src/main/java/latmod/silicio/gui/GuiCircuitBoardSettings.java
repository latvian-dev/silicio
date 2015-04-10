package latmod.silicio.gui;
import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.Silicio;
import latmod.silicio.gui.container.ContainerCircuitBoardSettings;
import latmod.silicio.item.modules.ICBModule;
import latmod.silicio.tile.CircuitBoard;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCircuitBoardSettings extends GuiLM
{
	public ButtonLM buttonBack;
	public CircuitBoard board;
	public ItemButtonLM itemButtons[];
	
	public GuiCircuitBoardSettings(ContainerCircuitBoardSettings c)
	{
		super(c, Silicio.mod.getLocation("textures/gui/circuitBoardSettings.png"));
		ySize = 138;
		
		board = (CircuitBoard)c.inv;
		
		widgets.add(buttonBack = new ButtonLM(this, 146, 21, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				board.cable.clientOpenGui(0);
			}
		});
		
		itemButtons = new ItemButtonLM[board.items.length];
		
		for(int y = 0; y < 2; y++)
		for(int x = 0; x < 6; x++)
		{
			final int id = x + y * 6;
			
			if(board.items[id] != null && board.items[id].getItem() instanceof ICBModule)
			{
				itemButtons[id] = new ItemButtonLM(this, 9 + x * 22, 10 + y * 22, 18, 18)
				{
					public void onButtonPressed(int b)
					{
						playClickSound();
						mc.displayGuiScreen(new GuiModuleSettings(container.player, board, (ICBModule)board.items[id].getItem(), id));
					}
				};
				
				itemButtons[id].setItem(board.items[id]);
				//itemButtons[id].setBackground(button_pressed);
				widgets.add(itemButtons[id]);
			}
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		buttonBack.render(Icons.back);
		
		for(int i = 0; i < itemButtons.length; i++)
		{
			if(itemButtons[i] != null)
				itemButtons[i].render();
		}
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		FastList<String> al = new FastList<String>();
		
		if(buttonBack.mouseOver(mx, my))
			al.add(LC.mod.translate("back"));
		
		for(int i = 0; i < itemButtons.length; i++)
		{
			if(itemButtons[i] != null && itemButtons[i].item != null && itemButtons[i].mouseOver(mx, my))
				al.add(itemButtons[i].item.getDisplayName());
		}
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}