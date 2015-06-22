package latmod.silicio.gui;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.silicio.gui.container.ContainerModuleCopier;
import latmod.silicio.tile.cb.TileModuleCopier;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiModuleCopier extends GuiLM
{
	public static final TextureCoords texBar = new TextureCoords(GuiModule.getTex("moduleCopier.png"), 176, 0, 22, 15);
	
	public TileModuleCopier tile;
	
	public GuiModuleCopier(ContainerModuleCopier c)
	{
		super(c, texBar.texture);
		tile = (TileModuleCopier)c.inv;
		xSize = 176;
		ySize = 166;
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		texBar.render(this, 86, 35, (int)(22 * tile.getProgressF()), 15);
	}
}