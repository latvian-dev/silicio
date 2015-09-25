package latmod.silicio.item.modules;

import latmod.core.util.FastList;
import latmod.silicio.tile.cb.*;

public interface ITankProvider
{
	public void updateTankNet(ModuleEntry e, FastList<TankEntry> l);
}