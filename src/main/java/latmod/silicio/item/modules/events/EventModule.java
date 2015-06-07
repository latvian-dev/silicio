package latmod.silicio.item.modules.events;

import latmod.ftbu.core.util.LatCore;
import latmod.silicio.tile.cb.ModuleEntry;
import net.minecraft.item.ItemStack;

public class EventModule extends EventBoard
{
	public final ModuleEntry module;
	
	public EventModule(ModuleEntry e)
	{ super(e.controller, e.board); module = e; }
	
	public ItemStack item()
	{ return module.stack; }
	
	public int hashCode()
	{ return LatCore.hashCode(super.hashCode(), module.moduleID); }
	
	public int getChannel(int i)
	{ return module.item.getChannel(board, module.moduleID, i); }
	
	public boolean isEnabled(int i, int ch, boolean def)
	{ return isEnabled0(getChannel(i), ch, def); }
	
	public boolean setEnabled(int i)
	{ return setEnabled0(getChannel(i)); }
}