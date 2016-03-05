package latmod.silicio.api.tileentity;

import latmod.silicio.api.modules.ModuleContainer;
import net.minecraft.util.EnumFacing;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface IModuleSocketTile extends ICBNetTile
{
	ModuleContainer getModule(EnumFacing facing);
}
