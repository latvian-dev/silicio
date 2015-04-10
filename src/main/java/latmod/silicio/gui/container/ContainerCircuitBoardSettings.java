package latmod.silicio.gui.container;
import latmod.core.gui.ContainerLM;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCircuitBoardSettings extends ContainerLM
{
	public ContainerCircuitBoardSettings(EntityPlayer ep, CircuitBoard t)
	{
		super(ep, t);
		addPlayerSlots(56);
	}
}