package latmod.silicio.config;
import java.io.File;

import latmod.ftbu.api.config.*;
import latmod.ftbu.util.LatCoreMC;
import latmod.silicio.Silicio;

public class SilConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile(Silicio.mod.modID, new File(LatCoreMC.configFolder, "/LatMod/Silicio.txt"), true);
		SilConfigGeneral.load(configFile);
		ConfigFileRegistry.add(configFile);
		configFile.load();
	}
}