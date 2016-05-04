package latmod.silicio.tile;

import ftb.lib.api.tile.TileLM;
import latmod.lib.LMUtils;
import latmod.silicio.api.tile.cb.ICBController;
import latmod.silicio.api.tile.cb.ICBNetTile;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileCBNetwork extends TileLM implements ICBNetTile
{
	private UUID uuid;
	private ICBController controller;
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		if(tag.hasKey("UUID"))
		{
			uuid = LMUtils.fromString(tag.getString("UUID"));
		}
		else
		{
			uuid = null;
		}
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		
		if(uuid != null)
		{
			tag.setString("UUID", LMUtils.fromUUID(uuid));
		}
	}
	
	@Override
	public void readTileClientData(NBTTagCompound tag)
	{
		uuid = tag.hasKey("IDM") ? new UUID(tag.getLong("IDM"), tag.getLong("IDL")) : null;
	}
	
	@Override
	public void writeTileClientData(NBTTagCompound tag)
	{
		if(uuid != null)
		{
			tag.setLong("IDM", uuid.getMostSignificantBits());
			tag.setLong("IDL", uuid.getLeastSignificantBits());
		}
	}
	
	@Override
	public final UUID getUUID()
	{ return uuid; }
	
	@Override
	public final void setUUID(UUID id)
	{ uuid = id; }
	
	@Override
	public boolean isDeviceOnline(ICBController c)
	{ return true; }
}