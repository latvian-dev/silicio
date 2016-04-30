package latmod.silicio.tile;

import ftb.lib.BlockDimPos;
import ftb.lib.api.tile.TileLM;
import latmod.lib.LMUtils;
import latmod.silicio.api.tile.CBNetwork;
import latmod.silicio.api.tile.ICBController;
import latmod.silicio.api.tile.ICBNetTile;
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
	public void onLoad()
	{
		super.onLoad();
		CBNetwork.load(this);
	}
	
	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		CBNetwork.unload(this);
	}
	
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
		tag.setString("UUID", LMUtils.fromUUID(getUUID()));
	}
	
	@Override
	public void readTileClientData(NBTTagCompound tag)
	{
		uuid = new UUID(tag.getLong("IDM"), tag.getLong("IDL"));
	}
	
	@Override
	public void writeTileClientData(NBTTagCompound tag)
	{
		tag.setLong("IDM", getUUID().getMostSignificantBits());
		tag.setLong("IDL", getUUID().getLeastSignificantBits());
	}
	
	@Override
	public UUID getUUID()
	{
		if(uuid == null)
		{
			uuid = UUID.randomUUID();
		}
		
		return uuid;
	}
	
	@Override
	public boolean isDeviceAvailable(ICBController c)
	{ return true; }
	
	@Override
	public void setController(ICBController c)
	{ controller = c; }
	
	@Override
	public ICBController getController()
	{ return controller; }
	
	@Override
	public void onCBNetworkChanged(BlockDimPos pos)
	{
	}
}
