package latmod.silicio.modules;

import latmod.silicio.api.modules.ConnectionType;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;
import latmod.silicio.api.modules.ModuleIOConnection;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput extends Module
{
	public static final ModuleIOConnection IN = new ModuleIOConnection(0, "in", ConnectionType.INPUT);
	
	@Override
	public void init(ModuleContainer c)
	{
		c.addConnection(IN);
	}
	
	@Override
	public void onAdded(ModuleContainer c, EntityPlayerMP player)
	{
	}
	
	@Override
	public void onSignalChanged(ModuleContainer c, int id, boolean on)
	{
		if(on && id == c.getConnectionID(IN))
		{
		}
	}
}