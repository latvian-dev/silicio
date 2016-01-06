package latmod.silicio.item.modules;

import latmod.silicio.tile.cb.*;

import java.util.List;

public interface IInvProvider
{
	public void updateInvNet(ModuleEntry e, List<InvEntry> l);
}