package latmod.silicio.config;

import latmod.lib.config.*;

public class SilConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	
	public static final ConfigEntryBool disableAllCrafting = new ConfigEntryBool("disable_all_crafting", false);
	public static final ConfigEntryBool enableTERecipes = new ConfigEntryBool("enable_te_recipes", true).setInfo("Enable ThermalExpansion recipes");
	public static final ConfigEntryBool siliconRecipe = new ConfigEntryBool("silicon_recipe", true);
	
	public static void load(ConfigFile f)
	{
		group.add(disableAllCrafting);
		group.add(enableTERecipes);
		group.add(siliconRecipe);
		f.add(group);
	}
}