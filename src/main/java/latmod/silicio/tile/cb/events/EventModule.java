package latmod.silicio.tile.cb.events;

import latmod.lib.LMUtils;
import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.tile.cb.ModuleEntry;
import net.minecraft.item.ItemStack;

public class EventModule extends EventBoard
{
	public final ModuleEntry module;
	
	public EventModule(ModuleEntry e)
	{ super(e.net, e.board); module = e; }
	
	public ItemStack item()
	{ return module.stack; }
	
	public int hashCode()
	{ return LMUtils.hashCode(super.hashCode(), module.moduleID); }
	
	public int getChannel(int i)
	{ return ItemModule.getChannel(module.stack, i); }
	
	public boolean isEnabled(int i, int ch, boolean def)
	{ return isEnabled0(getChannel(i), ch, def); }
	
	public boolean setEnabled(int i)
	{ return setEnabled0(getChannel(i)); }
}