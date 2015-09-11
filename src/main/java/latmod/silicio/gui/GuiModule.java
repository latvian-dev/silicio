package latmod.silicio.gui;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.gui.*;
import latmod.silicio.Silicio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public abstract class GuiModule extends GuiLM
{
	public GuiModule(EntityPlayer ep, ResourceLocation tex)
	{ super(new ContainerEmpty(ep, null), tex); }
	
	public static ResourceLocation getTex(String s)
	{ return Silicio.mod.getLocation("textures/gui/" + s); }
}