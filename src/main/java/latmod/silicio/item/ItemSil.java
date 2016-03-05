package latmod.silicio.item;

import ftb.lib.LMMod;
import ftb.lib.api.item.ItemLM;
import latmod.silicio.Silicio;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemSil extends ItemLM
{
	public ItemSil(String s)
	{
		super(s);
	}
	
	public LMMod getMod()
	{ return Silicio.mod; }
}
