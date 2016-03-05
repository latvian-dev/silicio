package latmod.silicio.modules;

import latmod.silicio.api.modules.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleChatOutput implements IModule
{
	public static final ModuleChannel IN = new ModuleChannel(0, "in", ModuleChannel.Type.INPUT);
	
	public String getID()
	{ return "chat_output"; }
	
	public void init(ModuleContainer c)
	{
		c.addChannel(IN);
	}
	
	public void loadRecipes()
	{
	}
}