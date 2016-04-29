package latmod.silicio.modules;

import latmod.lib.IntList;
import latmod.silicio.api.modules.ConnectionType;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;
import latmod.silicio.api.modules.ModuleIOConnection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleTimer extends Module
{
	public static final ModuleIOConnection OUT = new ModuleIOConnection(0, "out", ConnectionType.OUTPUT);
	
	public ModuleTimer(String s)
	{
		super(s);
		setFlag(FLAG_PROVIDE_SIGNALS, true);
	}
	
	@Override
	public void init(ModuleContainer c)
	{
		c.addConnection(OUT);
	}
	
	@Override
	public void loadRecipes()
	{
	}
	
	@Override
	public void provideSignals(ModuleContainer c, IntList list)
	{
		if(c.tick % 20L == 0L) list.add(c.getConnectionID(OUT));
	}
}