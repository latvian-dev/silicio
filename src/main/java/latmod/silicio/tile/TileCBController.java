package latmod.silicio.tile;

import cofh.api.energy.*;
import latmod.lib.IntList;
import latmod.silicio.api.modules.ModuleContainer;
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
	private List<ModuleContainer> modules;
	
	private boolean hasConflict = false;
	private boolean updateNetwork = false;
	private int updateModules = 0;
	
	public TileCBController()
	{
		energyStorage = new EnergyStorage(1000000);
		signalMap = new HashMap<>();
		network = new ArrayList<>();
		modules = new ArrayList<>();
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
			network = CBNetwork.getTilesAround(this);
			updateModules = 2;
		}
		
		if(updateModules > 0)
		{
			if(updateModules >= 2)
			{
				modules.clear();
				
				for(ICBNetTile t : network)
				{
					if(t instanceof IModuleSocketTile)
					{
						modules.addAll(((IModuleSocketTile) t).getModules());
					}
				}
			}
			
			signalMap.clear();
			
			IntList signalList = new IntList();
			
			for(ModuleContainer c : modules)
			{
				c.module.provideSignals(c, signalList);
			}
			
			updateModules = 0;
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
	
	public List<ICBNetTile> getNetwork()
	{ return network; }
	
	public void updateModules(boolean refreshList)
	{ updateModules = refreshList ? 2 : 1; }
	
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{ return energyStorage.receiveEnergy(maxReceive, simulate); }
	
	public int getEnergyStored(EnumFacing from)
	{ return energyStorage.getEnergyStored(); }
	
	public int getMaxEnergyStored(EnumFacing from)
	{ return energyStorage.getMaxEnergyStored(); }
	
	public boolean canConnectEnergy(EnumFacing from)
	{ return true; }
}
