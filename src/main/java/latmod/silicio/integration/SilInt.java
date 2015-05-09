package latmod.silicio.integration;

import latmod.core.LatCoreMC;
import latmod.silicio.Silicio;

public class SilInt
{
	public static final void onLoadedAll()
	{
		load("ThermalExpansion", "latmod.silicio.integration.SilIntTE");
		load("ComputerCraft", "latmod.silicio.integration.SilIntCC");
	}
	
	private static final void load(String m, String c)
	{
		if(LatCoreMC.isModInstalled(m))
		{
			try
			{
				LatCoreMC.invokeStatic(c, "onLoaded");
				Silicio.mod.logger.info(m + " integration loaded");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				Silicio.mod.logger.warn("Failed to load " + m + " integration!");
			};
		}
	}
}