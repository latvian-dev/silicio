package latmod.silicio.integration;

import java.lang.reflect.Method;

import ftb.lib.FTBLib;
import latmod.silicio.Silicio;

public class SilInt
{
	public static final void onLoadedAll()
	{
		load("ThermalExpansion", "latmod.silicio.integration.SilIntTE");
		load("ComputerCraft", "latmod.silicio.integration.SilIntCC");
		//TODO: Use EventFTBULoaded.POST
	}
	
	private static final void load(String m, String c)
	{
		if(FTBLib.isModInstalled(m))
		{
			try
			{
				Class<?> clazz = Class.forName(c);
				Method method = clazz.getMethod("onLoaded");
				method.invoke(null);
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