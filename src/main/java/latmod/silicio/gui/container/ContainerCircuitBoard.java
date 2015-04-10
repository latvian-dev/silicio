package latmod.silicio.gui.container;
import latmod.core.gui.ContainerLM;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCircuitBoard extends ContainerLM
{
	public ContainerCircuitBoard(EntityPlayer ep, CircuitBoard t)
	{
		super(ep, t);
		
		for(int y = 0; y < 2; y++)
			for(int x = 0; x < 6; x++)
			addSlotToContainer(new Slot(t, x + y * 6, 9 + x * 22, 10 + y * 22));
		
		addPlayerSlots(56);
	}
}