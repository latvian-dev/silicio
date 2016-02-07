package latmod.silicio.multiparts;

import latmod.silicio.SilItems;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

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
	
	public void addCollisionBoxes(AxisAlignedBB mask, List<AxisAlignedBB> list, Entity e)
	{
		super.addCollisionBoxes(mask, list, e);
	}
}