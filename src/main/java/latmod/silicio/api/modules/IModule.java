package latmod.silicio.api.modules;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public interface IModule
{
	String getID();
	void init(ModuleContainer c);
	void loadRecipes();
}
