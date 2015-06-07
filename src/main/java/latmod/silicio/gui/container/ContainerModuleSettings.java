package latmod.silicio.gui.container;
import latmod.ftbu.core.gui.ContainerLM;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerModuleSettings extends ContainerLM
{
	public ContainerModuleSettings(EntityPlayer ep, CircuitBoard t)
	{
		super(ep, t);
		addPlayerSlots(60);
	}
}