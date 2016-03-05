package latmod.silicio.api.modules;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public interface IAddedRemovedModule extends IModule
{
	void onAdded(ModuleContainer c);
	void onRemoved(ModuleContainer c);
}
