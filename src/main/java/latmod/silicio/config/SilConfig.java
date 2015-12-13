package latmod.silicio.config;
import java.io.File;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.lib.config.ConfigFile;

public class SilConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("silicio", new File(FTBLib.folderConfig, "Silicio.json"));
		configFile.configGroup.setName("Silicio");
		SilConfigGeneral.load(configFile);
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}