package latmod.silicio.item.modules;

import latmod.silicio.tile.CircuitBoard;

public interface ISignalProvider extends ICBModule
{
	public void provideSignals(CircuitBoard cb, int MID);
}