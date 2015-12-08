package latmod.silicio.integration;

import java.lang.reflect.Method;

import ftb.lib.*;
import latmod.silicio.Silicio;

public class SilInt
{
	public static final void onLoadedAll()
	{
		load(OtherMods.THERMAL_EXPANSION, "latmod.silicio.integration.SilIntTE");
		load(OtherMods.COMPUTER_CRAFT, "latmod.silicio.integration.SilIntCC");
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