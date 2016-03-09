package latmod.silicio.client;

import ftb.lib.api.client.FTBLibClient;
import latmod.silicio.SilCommon;
import latmod.silicio.tile.TileLaserTX;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
	public void preInit()
	{
		FTBLibClient.addTileRenderer(TileLaserTX.class, new RenderLaserTX());
		//OBJLoader.instance.addDomain(Silicio.mod.getID());
	}
}