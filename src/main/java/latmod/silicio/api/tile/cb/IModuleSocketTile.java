package latmod.silicio.api.tile.cb;

import latmod.silicio.api.modules.ModuleContainer;
import net.minecraft.util.EnumFacing;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface IModuleSocketTile extends ICBNetTile
{
	boolean hasModules(EnumFacing facing);
	Collection<ModuleContainer> getModules(EnumFacing facing);
}