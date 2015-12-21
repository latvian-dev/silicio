package latmod.silicio.config;
import java.io.File;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.lib.config.*;

public class SilConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("silicio", new File(FTBLib.folderConfig, "Silicio.json"));
		configFile.configGroup.setName("Silicio");
		configFile.add(new ConfigGroup("general").addAll(SilConfigGeneral.class));
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}