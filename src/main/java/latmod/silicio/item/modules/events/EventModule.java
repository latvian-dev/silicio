package latmod.silicio.item.modules.events;

import latmod.core.util.LatCore;
import latmod.silicio.tile.cb.*;
import net.minecraft.item.ItemStack;

public class EventModule extends EventBoard
{
	public final ModuleEntry module;
	
	public EventModule(ModuleEntry e)
	{
		super(e.controller, e.board);
		module = e;
	}
	
	public ItemStack item()
	{ return module.stack; }
	
	public int hashCode()
	{ return LatCore.hashCode(super.hashCode(), module.moduleID); }
	
	public int getChannel(int i)
	{ return module.item.getChannel(board, module.moduleID, i); }
	
	public boolean isEnabled0(int ch0, int ch, boolean def)
	{ return controller.isEnabled(ch0, ch, def); }
	
	public boolean isEnabled(int i, int ch, boolean def)
	{ return isEnabled0(getChannel(i), ch, def); }
	
	public boolean setEnabled0(int ch)
	{ return controller.setEnabled(ch); }
	
	public boolean setEnabled(int i)
	{ return setEnabled0(getChannel(i)); }
}