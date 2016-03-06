package latmod.silicio.tile;

import cofh.api.energy.*;
import ftb.lib.FTBLib;
import latmod.lib.IntList;
import latmod.silicio.api.modules.*;
import latmod.silicio.api.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import java.util.*;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileCBController extends TileCBNetwork implements ICBController, IEnergyReceiver
{
	public final EnergyStorage energyStorage;
	private final IntList signalList;
	private List<ICBNetTile> network;
	private List<ModuleContainer> modules;
	
	private boolean hasConflict = false;
	private boolean updateNetwork = false;
	
	public TileCBController()
	{
		energyStorage = new EnergyStorage(1000000);
		signalList = new IntList().setDefVal(0);
		network = new ArrayList<>();
		modules = new ArrayList<>();
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void onLoad()
	{
		super.onLoad();
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, EnumFacing side, float x, float y, float z)
	{
		if(isServer())
		{
			FTBLib.printChat(ep, "Network: " + network.size());
			FTBLib.printChat(ep, "Modules: " + modules.size());
			FTBLib.printChat(ep, "Signals: " + signalList);
		}
		
		return true;
	}
	
	public void onUpdate()
	{
		if(!isServer()) return;
		
		if(updateNetwork)
		{
			updateNetwork = false;
			
			network = CBNetwork.getTilesAround(this);
			
			modules.clear();
			
			for(ICBNetTile t : network)
			{
				if(t instanceof IModuleSocketTile)
				{
					modules.addAll(((IModuleSocketTile) t).getModules());
				}
			}
		}
		
		if(!modules.isEmpty())
		{
			IntList signalList0 = signalList.copy();
			signalList.clear();
			
			IntList signalList1 = new IntList();
			
			for(ModuleContainer c : modules)
			{
				c.onUpdate();
				
				if(c.module.getFlag(Module.FLAG_PROVIDE_SIGNALS))
				{
					c.module.provideSignals(c, signalList1);
					
					for(int i = 0; i < signalList1.size(); i++)
					{
						int id = signalList1.get(i);
						if(id != 0 && !signalList.contains(id)) signalList.add(id);
					}
					
					signalList1.clear();
				}
			}
			
			if(!signalList0.equalsIgnoreOrder(signalList))
			{
				FTBLib.printChat(null, "Signals: " + signalList);
			}
		}
		else signalList.clear();
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
		return signalList.contains(id);
	}
	
	public List<ICBNetTile> getNetwork()
	{ return network; }
	
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{ return energyStorage.receiveEnergy(maxReceive, simulate); }
	
	public int getEnergyStored(EnumFacing from)
	{ return energyStorage.getEnergyStored(); }
	
	public int getMaxEnergyStored(EnumFacing from)
	{ return energyStorage.getMaxEnergyStored(); }
	
	public boolean canConnectEnergy(EnumFacing from)
	{ return true; }
}
