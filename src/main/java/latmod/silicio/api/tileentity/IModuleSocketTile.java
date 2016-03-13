package latmod.silicio.api.tileentity;

import latmod.silicio.api.modules.ModuleContainer;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface IModuleSocketTile extends ICBNetTile
{
	Collection<ModuleContainer> getModules();
}
