package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.cb.*;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiSelectChannels extends GuiLM
{
	public static final ResourceLocation thisTex = GuiModule.getTex("selectChannel.png");
	public static final TextureCoords iconSelChannel = new TextureCoords(thisTex, 180, 0);
	public static final TextureCoords iconInput = new TextureCoords(thisTex, 180, 10);
	public static final TextureCoords iconOutput = new TextureCoords(thisTex, 180, 18);
	public static final TextureCoords iconIOPressed = new TextureCoords(thisTex, 180, 26);
	public static final TextureCoords iconChannelEnabled = new TextureCoords(thisTex, 180, 34);
	public static final TextureCoords iconBack = new TextureCoords(thisTex, 166, 2);
	
	public CircuitBoard board;
	public ItemModule module;
	public int moduleID;
	
	public ButtonLM buttonBack;
	public ButtonLM[] buttonSelectIO;
	public ButtonLM[] allChannels;
	
	public int selectedIO = 0;
	
	public GuiSelectChannels(ContainerEmpty c, int id)
	{
		super(c, thisTex);
		
		xSize = 166;
		ySize = 98;
		board = (CircuitBoard)c.inv;
		moduleID = id;
		module = board.getModule(moduleID);
		
		widgets.add(buttonBack = new ButtonLM(this, 166, 2, 14, 18)
		{
			public void onButtonPressed(int b)
			{
				board.cable.clientOpenGui(TileCBCable.guiData(board.side, 2, moduleID));
				playClickSound();
			}
		});
		
		buttonBack.title = LC.mod.translate("button.back");
		
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
			widgets.add(buttonSelectIO[i]);
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
			widgets.add(b);
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
			
			widgets.add(allChannels[i]);
		}
	}
	
	private void sendSetChannel(int ch)
	{ board.cable.clientSetChannel(board.side, moduleID, selectedIO, ch); }
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		for(int i = 0; i < buttonSelectIO.length; i++)
		{
			if(module.getChannelType(buttonSelectIO[i].customID).isInput())
				buttonSelectIO[i].render(iconInput);
			else buttonSelectIO[i].render(iconOutput);
			
			if(selectedIO == i)
				iconIOPressed.render(this, buttonSelectIO[i].posX, buttonSelectIO[i].posY, 8, 8);
		}
		
		for(ButtonLM b : allChannels)
		{
			if(b.customID == ItemModule.getChannelID(module, board.items[moduleID], selectedIO))
				iconSelChannel.render(this, b.posX - 1, b.posY - 1, 10, 10);
			
			if(b.customID >= 0 && board.cable.controller != null)
			{
				if(board.cable.controller.channels.contains(b.customID))
					iconChannelEnabled.render(this, b.posX, b.posY, 8, 8);
			}
		}
		
		iconBack.render(this, buttonBack.posX, buttonBack.posY, buttonBack.width, buttonBack.height);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		//board = ((TileCBCable)board.cable.getWorldObj().getTileEntity(board.cable.xCoord, board.cable.yCoord, board.cable.zCoord)).getBoard(board.side);
		//module = (ICBModule)board.items[moduleID].getItem();
		
		super.drawScreen(mx, my, f);
	}
}