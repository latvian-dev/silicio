package latmod.silicio.gui;

import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.core.util.FastList;
import latmod.silicio.item.modules.*;
import latmod.silicio.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiSelectChannels extends GuiModule
{
	public static final ResourceLocation thisTex = getTex("selectChannel.png");
	public static final TextureCoords iconSelChannel = new TextureCoords(thisTex, 168, 0);
	public static final TextureCoords iconInput = new TextureCoords(thisTex, 168, 10);
	public static final TextureCoords iconOutput = new TextureCoords(thisTex, 168, 18);
	public static final TextureCoords iconIOPressed = new TextureCoords(thisTex, 168, 26);
	
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
		xSize = 168;
		ySize = 89;
		board = cb;
		module = m;
		moduleID = id;
		
		widgets.add(buttonBack = new ButtonLM(this, 156, 1, 11, 11)
		{
			public void onButtonPressed(int b)
			{
				mc.displayGuiScreen(new GuiModuleSettings(container.player, board, module, moduleID));
				playClickSound();
			}
		});
		
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
			widgets.add(buttonSelectChannel[i]);
		}
		
		widgets.add(buttonNoChannel = new ButtonLM(this, 7, 24, 8, 8)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				sendSetChannel(customID);
			}
		});
		
		buttonNoChannel.customID = 0;
		allChannels.add(buttonNoChannel);
		
		for(int i = 0; i < 16; i++)
		{
			ButtonLM b = new ButtonLM(this, 16 + i * 9, 24, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					sendSetChannel(customID);
				}
			};
			
			b.customID = -(i + 1);
			
			widgets.add(b);
			allChannels.add(b);
		}
		
		for(int i = 0; i < 64; i++)
		{
			int bx = i % 16;
			int by = i / 16;
			
			ButtonLM b = new ButtonLM(this, 16 + bx * 9, 45 + by * 9, 8, 8)
			{
				public void onButtonPressed(int b)
				{
					playClickSound();
					sendSetChannel(customID);
				}
			};
			
			b.customID = i + 1;
			
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
				buttonSelectChannel[i].render(iconIOPressed);
		}
		
		for(int i = 0; i < allChannels.size(); i++)
		{
			ButtonLM b = allChannels.get(i);
			
			if(b.customID == ItemModule.getChannelID(module, board.items[moduleID], selectedChannel))
			{
				iconSelChannel.render(this, b.posX - 1, b.posY - 1, 10, 10);
				return;
			}
		}
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		board = ((TileCBCable)board.cable.getWorldObj().getTileEntity(board.cable.xCoord, board.cable.yCoord, board.cable.zCoord)).getBoard(board.side);
		module = (ICBModule)board.items[moduleID].getItem();
		
		super.drawScreen(mx, my, f);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		FastList<String> al = new FastList<String>();
		
		for(int i = 0; i < buttonSelectChannel.length; i++)
		{
			if(buttonSelectChannel[i].mouseOver(mx, my))
				al.add(module.getChannelName(i));
		}
		
		if(buttonBack.mouseOver(mx, my))
			al.add(LC.mod.translate("back"));
		
		else for(int i = 0; i < allChannels.size(); i++)
		{
			ButtonLM b = allChannels.get(i);
			
			if(b.mouseOver(mx, my))
			{
				al.add(CBChannel.channelToString(b.customID));
				break;
			}
		}
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}