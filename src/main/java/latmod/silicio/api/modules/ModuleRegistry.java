package latmod.silicio.api.modules;

import latmod.silicio.modules.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleRegistry
{
	private static final Map<String, Module> map = new HashMap<>();
	
	public static void init()
	{
		register(new ModuleTimer("timer"));
		register(new ModuleChatOutput("chat_out"));
		
		MinecraftForge.EVENT_BUS.post(new RegistryEvent());
	}
	
	public static void register(Module m)
	{ map.put(m.getID(), m); }
	
	public static Collection<Module> modules()
	{ return map.values(); }
	
	public static Module get(String s)
	{
		if(s == null || s.isEmpty()) return null;
		return map.get(s);
	}
	
	public static Module getFromStack(ItemStack item)
	{
		if(item != null && item.getItem() instanceof IModuleItem)
		{
			return ((IModuleItem) item.getItem()).getModule(item);
		}
		
		return null;
	}
	
	/**
	 * Created by LatvianModder on 06.03.2016.
	 */
	public static class RegistryEvent extends Event
	{ }
}