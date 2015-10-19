package latmod.silicio.config;
import java.io.File;

import ftb.lib.mod.FTBLib;
import latmod.ftbu.api.config.ConfigListRegistry;
import latmod.lib.config.ConfigFile;
import latmod.silicio.Silicio;

public class SilConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile(Silicio.mod.modID, new File(FTBLib.folderConfig, "Silicio.json"), true);
		SilConfigGeneral.load(configFile);
		ConfigListRegistry.add(configFile);
		configFile.load();
	}
}