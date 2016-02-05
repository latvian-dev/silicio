package latmod.silicio.client;

import latmod.silicio.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
	public void preInit()
	{
		SilItems.registerModels();
		//RenderCBCable.instance.register(TileCBCable.class);
	}
}