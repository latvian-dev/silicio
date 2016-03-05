package latmod.silicio.modules;

import latmod.silicio.api.modules.*;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class ModuleTimer extends Module
{
	public static final ModuleIOConnection OUT = new ModuleIOConnection(0, "out", ConnectionType.OUTPUT);
	
	public ModuleTimer(String s)
	{
		super(s);
	}
	
	public void init(ModuleContainer c)
	{
		c.addConnection(OUT);
	}
	
	public void loadRecipes()
	{
	}
	
	public boolean provideSignal(ModuleContainer c)
	{
		return c.tick % 20L == 0L;
	}
}