package latmod.silicio.client;
import latmod.silicio.SilCommon;
import latmod.silicio.client.render.world.RenderCBCable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
	public void preInit(FMLPreInitializationEvent e)
	{
		RenderCBCable.instance.register();
	}
}