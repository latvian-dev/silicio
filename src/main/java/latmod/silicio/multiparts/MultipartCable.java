package latmod.silicio.multiparts;

import latmod.silicio.SilItems;

/**
 * Created by LatvianModder on 07.02.2016.
 */
public class MultipartCable extends MultipartBase
{
	public MultipartCable()
	{ super(SilItems.b_cable); }
	
	public String getModelPath()
	{ return "silicio:cable"; }
	
	public MultipartBase createNew()
	{ return new MultipartCable(); }
}