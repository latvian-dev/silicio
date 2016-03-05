package latmod.silicio.modules;

import latmod.silicio.api.modules.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput extends Module
{
	public static final ModuleIOConnection IN = new ModuleIOConnection(0, "in", ConnectionType.INPUT);
	
	public ModuleChatOutput(String s)
	{ super(s); }
	
	public void init(ModuleContainer c)
	{
		c.addConnection(IN);
	}
	
	public void loadRecipes()
	{
	}
	
	public void onSignalChanged(ModuleContainer c, int id, boolean on)
	{
		if(on && id == c.getConnectionID(IN))
		{
		}
	}
}