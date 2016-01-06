package latmod.silicio.item.modules;

import latmod.silicio.tile.cb.*;

import java.util.List;

public interface ITankProvider
{
	public void updateTankNet(ModuleEntry e, List<TankEntry> l);
}