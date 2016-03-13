package latmod.silicio.client;

import ftb.lib.api.client.FTBLibClient;
import latmod.silicio.SilCommon;
import latmod.silicio.tile.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
	public void preInit()
	{
		FTBLibClient.addTileRenderer(TileAntimatter.class, new RenderAntimatter());
		FTBLibClient.addTileRenderer(TileLaserTX.class, new RenderLaserTX());
		FTBLibClient.addTileRenderer(TileTurret.class, new RenderTurret());
		//OBJLoader.instance.addDomain(Silicio.mod.getID());
	}
}