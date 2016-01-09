package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.TextureCoords;
import ftb.lib.gui.GuiLM;
import latmod.silicio.gui.container.ContainerModuleCopier;
import latmod.silicio.tile.cb.TileModuleCopier;

@SideOnly(Side.CLIENT)
public class GuiModuleCopier extends GuiLM
{
	public static final TextureCoords texBar = new TextureCoords(GuiModule.getTex("moduleCopier.png"), 176, 0, 22, 15);
	
	public TileModuleCopier tile;
	
	public GuiModuleCopier(ContainerModuleCopier c)
	{
		super(c, texBar.texture);
		tile = (TileModuleCopier) c.inv;
		xSize = 176;
		ySize = 166;
	}
	
	public void addWidgets()
	{
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		render(texBar, 86, 35, (int) (22 * tile.getProgressF()), 15);
	}
}