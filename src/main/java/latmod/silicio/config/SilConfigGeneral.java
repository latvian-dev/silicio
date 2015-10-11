package latmod.silicio.config;

import latmod.lib.config.*;

public class SilConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	
	public static final ConfigEntryBool disableAllCrafting = new ConfigEntryBool("disableAllCrafting", false);
	public static final ConfigEntryBool enableTERecipes = new ConfigEntryBool("enableTERecipes", true);
	public static final ConfigEntryBool siliconRecipe = new ConfigEntryBool("siliconRecipe", true);
	
	public static void load(ConfigFile f)
	{
		group.add(disableAllCrafting);
		group.add(enableTERecipes);
		group.add(siliconRecipe);
		f.add(group);
	}
}