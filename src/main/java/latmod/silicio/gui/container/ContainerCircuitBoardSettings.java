package latmod.silicio.gui.container;

import ftb.lib.gui.ContainerLM;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCircuitBoardSettings extends ContainerLM
{
	public ContainerCircuitBoardSettings(EntityPlayer ep, CircuitBoard t)
	{
		super(ep, t);
		addPlayerSlots(56);
	}
}