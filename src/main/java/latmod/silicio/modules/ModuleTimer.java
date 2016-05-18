package latmod.silicio.modules;

import latmod.silicio.api.SignalChannel;
import latmod.silicio.api.modules.EnumModuleIO;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleTimer extends Module
{
	public ModuleTimer()
	{
		setFlag(FLAG_PROVIDE_SIGNALS, true);
	}
	
	@Override
	public void init(ModuleContainer c)
	{
		c.addConnection(EnumModuleIO.OUT_1);
	}
	
	@Override
	public void provideSignals(ModuleContainer c, Collection<SignalChannel> list)
	{
		if(c.tick % 20L == 0L)
		{
			list.add(c.getChannel(EnumModuleIO.OUT_1));
		}
	}
}