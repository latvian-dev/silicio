package latmod.silicio.api.tile.cb;

import latmod.silicio.api.modules.ModuleContainer;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ICBModuleProvider extends ICBNetTile
{
	boolean hasModules();
	Collection<ModuleContainer> getModules();
}