package latmod.silicio.api.modules;

import latmod.silicio.modules.ModuleChatOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ModuleRegistry
{
	private static final Map<String, IModule> map = new HashMap<>();
	
	public static void init()
	{
		register(new ModuleChatOutput());
		
		MinecraftForge.EVENT_BUS.post(new RegisterModulesEvent());
	}
	
	public static void register(IModule m)
	{ map.put(m.getID(), m); }
	
	public static Collection<IModule> modules()
	{ return map.values(); }
	
	public static IModule get(String s)
	{
		if(s == null || s.isEmpty()) return null;
		return map.get(s);
	}
	
	public static IModule getFromStack(ItemStack item)
	{
		if(item != null && item.getItem() instanceof IModuleItem)
		{
			return ((IModuleItem) item.getItem()).getModule(item);
		}
		
		return null;
	}
}