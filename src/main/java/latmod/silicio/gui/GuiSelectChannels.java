package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.TextureCoords;
import ftb.lib.gui.*;
import ftb.lib.gui.widgets.ButtonLM;
import latmod.ftbu.util.client.FTBULang;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.tile.cb.*;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiSelectChannels extends GuiLM
{
	public static final ResourceLocation thisTex = GuiModule.getTex("selectChannel.png");
	public static final TextureCoords iconSelChannel = new TextureCoords(thisTex, 180, 0, 10, 10);
	public static final TextureCoords iconInput = new TextureCoords(thisTex, 180, 10, 8, 8);
	public static final TextureCoords iconOutput = new TextureCoords(thisTex, 180, 18, 8, 8);
	public static final TextureCoords iconIOPressed = new TextureCoords(thisTex, 180, 26, 8, 8);
	public static final TextureCoords iconChannelEnabled = new TextureCoords(thisTex, 180, 34, 8, 8);
	public static final TextureCoords iconBack = new TextureCoords(thisTex, 166, 2, 14, 18);
	
	public CircuitBoard board;
	public ItemModule module;
	public int moduleID;
	
	public final ButtonLM buttonBack;
	public final ButtonLM[] buttonSelectIO;
	public final ButtonLM[] allChannels;
	
	public int selectedIO = 0;
	
	public GuiSelectChannels(ContainerEmpty c, int id)
	{
		super(c, thisTex);
		
		xSize = 166;
		ySize = 98;
		board = (CircuitBoard)c.inv;
		moduleID = id;
		module = board.getModule(moduleID);
		
		buttonBack = new ButtonLM(this, 166, 2, iconBack.widthI, iconBack.heightI)
		{
			public void onButtonPressed(int b)
			{
				board.cable.clientOpenGui(TileCBCable.guiData(board.side, 2, moduleID));
				playClickSound();
			}
		};
		
		buttonBack.title = FTBULang.button_back();
		
		int chCount = module.getChannelCount();
		if(chCount > 16) chCount = 16;
		
		buttonSelectIO = new ButtonLM[chCount];
		
		for(int i = 0; i < chCount; i++)
		{
			buttonSelectIO[i] = new ButtonLM(this, 7 + i * 9, 7, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					selectedIO = customID;
					playClickSound();
				}
			};
			
			buttonSelectIO[i].customID = i;
			buttonSelectIO[i].title = module.getChannelName(i);
		}
		
		allChannels = new ButtonLM[TileCBController.MAX_CHANNEL + 1];
		
		{
			ButtonLM b = new ButtonLM(this, 151, 7, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					sendSetChannel(customID);
				}
			};
			
			b.customID = -1;
			b.title = "Disabled";
			allChannels[allChannels.length - 1] = b;
		}
		
		for(int i = 0; i < TileCBController.MAX_CHANNEL; i++)
		{
			int bx = i % 16;
			int by = i / 16;
			
			allChannels[i] = new ButtonLM(this, 16 + bx * 9, 20 + by * 9, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					sendSetChannel(customID);
				}
			};
			
			allChannels[i].customID = i;
			allChannels[i].title = TileCBController.getChannelName(i);
		}
	}
	
	public void addWidgets()
	{
		mainPanel.add(buttonBack);
		mainPanel.addAll(buttonSelectIO);
		mainPanel.addAll(allChannels);
	}
	
	private void sendSetChannel(int ch)
	{ board.cable.clientSetChannel(board.side, moduleID, selectedIO, ch); }
	
	public void drawBackground()
	{
		super.drawBackground();
		
		for(int i = 0; i < buttonSelectIO.length; i++)
		{
			if(module.getChannelType(buttonSelectIO[i].customID).isInput())
				buttonSelectIO[i].render(iconInput);
			else buttonSelectIO[i].render(iconOutput);
			
			if(selectedIO == i)
				render(iconIOPressed, buttonSelectIO[i].posX, buttonSelectIO[i].posY, 8, 8);
		}
		
		for(ButtonLM b : allChannels)
		{
			if(b.customID == ItemModule.getChannel(board.items[moduleID], selectedIO))
				render(iconSelChannel, b.posX - 1, b.posY - 1, 10, 10);
			
			if(b.customID >= 0 && board.cable.getCBNetwork().hasController())
			{
				if(board.cable.getCBNetwork().controller.channels.contains(b.customID))
					render(iconChannelEnabled, b.posX, b.posY, 8, 8);
			}
		}
		
		render(iconBack, buttonBack.posX, buttonBack.posY, buttonBack.width, buttonBack.height);
	}
}