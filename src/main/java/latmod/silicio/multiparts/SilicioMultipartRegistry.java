package latmod.silicio.multiparts;

import mcmultipart.multipart.MultipartRegistry;

/**
 * Created by LatvianModder on 07.02.2016.
 */
public class SilicioMultipartRegistry
{
	public static void init()
	{
		register(new MultipartCable(), "silicio:cable");
	}
	
	public static void register(MultipartBase mp, String id)
	{
		MultipartRegistry.registerPart(mp.getClass(), id);
		MultipartRegistry.registerPartConverter(mp);
	}
}