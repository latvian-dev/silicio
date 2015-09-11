package latmod.silicio.client;
import cpw.mods.fml.relauncher.*;
import latmod.silicio.SilCommon;
import latmod.silicio.client.render.tile.RenderCBCable;
import latmod.silicio.tile.cb.TileCBCable;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
	public void preInit()
	{
		RenderCBCable.instance.register(TileCBCable.class);
	}
}