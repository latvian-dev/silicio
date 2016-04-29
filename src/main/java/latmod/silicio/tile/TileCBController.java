package latmod.silicio.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ftb.lib.FTBLib;
import latmod.lib.IntList;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;
import latmod.silicio.api.tileentity.CBNetwork;
import latmod.silicio.api.tileentity.ICBController;
import latmod.silicio.api.tileentity.ICBNetTile;
import latmod.silicio.api.tileentity.IModuleSocketTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileCBController extends TileCBNetwork implements ICBController, IEnergyReceiver
{
	public final EnergyStorage energyStorage;
	private final IntList signalList;
	private List<ICBNetTile> network;
	private List<ModuleContainer> modules;
	
	private byte refreshTick = 0;
	private boolean hasConflict = false;
	private boolean updateNetwork = false;
	
	public TileCBController()
	{
		energyStorage = new EnergyStorage(1000000);
		signalList = new IntList().setDefVal(0);
		network = new ArrayList<>();
		modules = new ArrayList<>();
	}
	
	@Override
	public EnumSync getSync()
	{ return EnumSync.RERENDER; }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		refreshTick = tag.getByte("Tick");
		signalList.clear();
		signalList.addAll(tag.getIntArray("Signals"));
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setByte("Tick", refreshTick);
		tag.setIntArray("Signals", signalList.toArray());
	}
	
	@Override
	public void readTileClientData(NBTTagCompound tag)
	{
		signalList.clear();
		signalList.addAll(tag.getIntArray("S"));
	}
	
	@Override
	public void writeTileClientData(NBTTagCompound tag)
	{
		tag.setIntArray("S", signalList.toArray());
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		updateNetwork = true;
	}
	
	@Override
	public boolean onRightClick(EntityPlayer ep, ItemStack is, EnumFacing side, EnumHand hand, float x, float y, float z)
	{
		if(getSide().isServer())
		{
			FTBLib.printChat(ep, "Network: " + network.size());
			FTBLib.printChat(ep, "Modules: " + modules.size());
			FTBLib.printChat(ep, "Signals: " + signalList);
		}
		
		return true;
	}
	
	@Override
	public void setController(ICBController c)
	{
	}
	
	@Override
	public ICBController getController()
	{ return this; }
	
	@Override
	public void onUpdate()
	{
		if(getSide().isClient()) return;
		
		if(refreshTick >= 20)
		{
			updateNetwork = true;
			refreshTick = 0;
		}
		else refreshTick++;
		
		if(updateNetwork)
		{
			boolean hc = hasConflict;
			hasConflict = false;
			
			updateNetwork = false;
			
			for(ICBNetTile t : network)
			{
				t.setController(null);
				t.onCBNetworkChanged(getPos());
			}
			
			network.clear();
			network = CBNetwork.getTilesAround(this);
			
			for(ICBNetTile t : network)
			{
				t.setController(this);
				if(t instanceof ICBController) hasConflict = true;
				t.onCBNetworkChanged(getPos());
			}
			
			modules.clear();
			
			for(ICBNetTile t : network)
			{
				if(t instanceof IModuleSocketTile)
				{
					Collection<ModuleContainer> mc = ((IModuleSocketTile) t).getModules(null);
					
					if(mc != null && !mc.isEmpty())
					{
						modules.addAll(mc);
					}
				}
			}
			
			if(hc != hasConflict) markDirty();
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
			
			Map<Integer, Boolean> diffMap = signalList0.getDifferenceMap(signalList);
			
			if(!diffMap.isEmpty())
			{
				FTBLib.printChat(null, "Signals: " + diffMap);
			}
		}
		else signalList.clear();
	}
	
	@Override
	public void onCBNetworkChanged(BlockPos pos)
	{
		updateNetwork = true;
	}
	
	@Override
	public void onBroken(IBlockState state)
	{
		for(ICBNetTile t : network)
		{
			t.setController(null);
		}
		
		super.onBroken(state);
	}
	
	@Override
	public boolean hasConflict()
	{ return hasConflict; }
	
	@Override
	public boolean getSignalState(int id)
	{
		if(id == 0) return false;
		return signalList.contains(id);
	}
	
	@Override
	public List<ICBNetTile> getNetwork()
	{ return network; }
	
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{ return energyStorage.receiveEnergy(maxReceive, simulate); }
	
	@Override
	public int getEnergyStored(EnumFacing from)
	{ return energyStorage.getEnergyStored(); }
	
	@Override
	public int getMaxEnergyStored(EnumFacing from)
	{ return energyStorage.getMaxEnergyStored(); }
	
	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{ return true; }
}
