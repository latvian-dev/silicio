package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiSelectChannels extends GuiModule
{
	public static final ResourceLocation thisTex = getTex("selectChannel.png");
	public static final TextureCoords iconSelChannel = new TextureCoords(thisTex, 180, 0);
	public static final TextureCoords iconInput = new TextureCoords(thisTex, 180, 10);
	public static final TextureCoords iconOutput = new TextureCoords(thisTex, 180, 18);
	public static final TextureCoords iconIOPressed = new TextureCoords(thisTex, 180, 26);
	public static final TextureCoords iconChannelEnabled = new TextureCoords(thisTex, 180, 34);
	public static final TextureCoords iconBack = new TextureCoords(thisTex, 166, 2);
	
	public CircuitBoard board;
	public ICBModule module;
	public int moduleID;
	
	public ButtonLM buttonBack;
	public ButtonLM buttonNoChannel;
	public ButtonLM[] buttonSelectChannel;
	public FastList<ButtonLM> allChannels = new FastList<ButtonLM>();
	
	public int selectedChannel = 0;
	
	public GuiSelectChannels(EntityPlayer ep, CircuitBoard cb, ICBModule m, int id)
	{
		super(ep, thisTex);
		xSize = 166;
		ySize = 98;
		board = cb;
		module = m;
		moduleID = id;
		
		widgets.add(buttonBack = new ButtonLM(this, 166, 2, 14, 18)
		{
			public void onButtonPressed(int b)
			{
				mc.displayGuiScreen(new GuiModuleSettings(container.player, board, module, moduleID));
				playClickSound();
			}
		});
		
		buttonBack.title = LC.mod.translate("button.back");
		
		int chCount = m.getChannelCount();
		if(chCount > 16) chCount = 16;
		
		buttonSelectChannel = new ButtonLM[chCount];
		
		for(int i = 0; i < chCount; i++)
		{
			buttonSelectChannel[i] = new ButtonLM(this, 7 + i * 9, 7, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					selectedChannel = customID;
					playClickSound();
				}
			};
			
			buttonSelectChannel[i].customID = i;
			buttonSelectChannel[i].title = module.getChannelName(i);
			widgets.add(buttonSelectChannel[i]);
		}
		
		widgets.add(buttonNoChannel = new ButtonLM(this, 151, 7, 8, 8)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				sendSetChannel(customID);
			}
		});
		
		buttonNoChannel.customID = -1;
		buttonNoChannel.title = CBChannel.NONE.name;
		allChannels.add(buttonNoChannel);
		
		for(int i = 0; i < cb.cable.controller.channels.length; i++)
		{
			int bx = i % 16;
			int by = i / 16;
			
			ButtonLM b = new ButtonLM(this, 16 + bx * 9, 20 + by * 9, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					sendSetChannel(customID);
				}
			};
			
			b.customID = i;
			b.title = cb.cable.controller.channels[b.customID].name;
			
			widgets.add(b);
			allChannels.add(b);
		}
	}
	
	private void sendSetChannel(int ch)
	{ board.cable.clientSetChannel(board.side.ordinal(), moduleID, selectedChannel, ch); }
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		for(int i = 0; i < buttonSelectChannel.length; i++)
		{
			if(module.getChannelType(buttonSelectChannel[i].customID).isInput())
				buttonSelectChannel[i].render(iconInput);
			else buttonSelectChannel[i].render(iconOutput);
			
			if(selectedChannel == i)
				iconIOPressed.render(this, buttonSelectChannel[i].posX, buttonSelectChannel[i].posY, 8, 8);
		}
		
		for(int i = 0; i < allChannels.size(); i++)
		{
			ButtonLM b = allChannels.get(i);
			
			if(b.customID == ItemModule.getChannelID(module, board.items[moduleID], selectedChannel))
				iconSelChannel.render(this, b.posX - 1, b.posY - 1, 10, 10);
			
			if(b.customID >= 0 && board.cable.controller != null)
			{
				if(board.cable.controller.channels[b.customID].isEnabled())
					iconChannelEnabled.render(this, b.posX, b.posY, 8, 8);
			}
		}
		
		iconBack.render(this, buttonBack.posX, buttonBack.posY, buttonBack.width, buttonBack.height);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		board = ((TileCBCable)board.cable.getWorldObj().getTileEntity(board.cable.xCoord, board.cable.yCoord, board.cable.zCoord)).getBoard(board.side);
		module = (ICBModule)board.items[moduleID].getItem();
		
		super.drawScreen(mx, my, f);
	}
}