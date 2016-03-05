package latmod.silicio.tile;

import cofh.api.energy.*;
import latmod.silicio.api.tileentity.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileCBController extends TileCBNetwork implements ICBController, IEnergyReceiver
{
	public final EnergyStorage energyStorage;
	private final Map<Integer, Integer> signalMap;
	private List<ICBNetTile> network;
	private boolean hasConflict = false;
	private boolean updateNetwork = false;
	
	public TileCBController()
	{
		energyStorage = new EnergyStorage(1000000);
		signalMap = new HashMap<>();
		network = new ArrayList<>();
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void onLoad()
	{
		super.onLoad();
	}
	
	public void onUpdate()
	{
		if(updateNetwork)
		{
			updateNetwork = false;
			
			signalMap.clear();
			network = CBNetwork.getTilesAround(this);
		}
	}
	
	public void onCBNetworkChanged(BlockPos pos)
	{
		updateNetwork = true;
	}
	
	public boolean hasConflict()
	{ return hasConflict; }
	
	public boolean getSignalState(int id)
	{
		if(id == 0) return false;
		Integer signal = signalMap.get(id);
		return signal != null && signal > 0;
	}
	
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{ return energyStorage.receiveEnergy(maxReceive, simulate); }
	
	public int getEnergyStored(EnumFacing from)
	{ return energyStorage.getEnergyStored(); }
	
	public int getMaxEnergyStored(EnumFacing from)
	{ return energyStorage.getMaxEnergyStored(); }
	
	public boolean canConnectEnergy(EnumFacing from)
	{ return true; }
}
