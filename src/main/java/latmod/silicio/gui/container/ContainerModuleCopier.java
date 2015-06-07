package latmod.silicio.gui.container;
import latmod.ftbu.core.gui.ContainerLM;
import latmod.silicio.tile.cb.TileModuleCopier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerModuleCopier extends ContainerLM
{
	public ContainerModuleCopier(EntityPlayer ep, TileModuleCopier t)
	{
		super(ep, t);
		
		addSlotToContainer(new Slot(t, 0, 34, 35));
		addSlotToContainer(new Slot(t, 1, 59, 35));
		addSlotToContainer(new Slot(t, 2, 122, 35));
		
		addPlayerSlots(84);
	}
}