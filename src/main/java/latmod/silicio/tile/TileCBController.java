package latmod.silicio.tile;

import ftb.lib.BlockDimPos;
import ftb.lib.FTBLib;
import latmod.lib.IntList;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;
import latmod.silicio.api.tile.cb.ICBController;
import latmod.silicio.api.tile.cb.ICBNetTile;
import latmod.silicio.api.tile.cb.IModuleSocketTile;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileCBController extends TileCBNetwork implements ICBController
{
	public final SilEnergyTank energyTank;
	private final IntList signalList;
	private List<ICBNetTile> network;
	private List<ModuleContainer> modules;
	
	private byte refreshTick = 0;
	private boolean hasConflict = false;
	private boolean updateNetwork = false;
	
	public TileCBController()
	{
		energyTank = new SilEnergyTank(100000D);
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
		super.readTileClientData(tag);
		signalList.clear();
		signalList.addAll(tag.getIntArray("S"));
	}
	
	@Override
	public void writeTileClientData(NBTTagCompound tag)
	{
		super.writeTileClientData(tag);
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
		if(getSide().isClient()) { return; }
		
		if(refreshTick >= 20)
		{
			updateNetwork = true;
			refreshTick = 0;
		}
		else { refreshTick++; }
		
		if(updateNetwork)
		{
			boolean hc = hasConflict;
			hasConflict = false;
			
			BlockDimPos dimPos = getDimPos();
			
			for(ICBNetTile t : network)
			{
				t.setController(null);
			}
			
			network.clear();
			//FIXME: network = SilNet.CIRCUIT_BOARD.getTilesAround(ICBNetTile.class, dimPos, 32D);
			
			for(ICBNetTile t : network)
			{
				t.setController(this);
				if(t instanceof ICBController) { hasConflict = true; }
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
			
			if(hc != hasConflict)
			{
				markDirty();
			}
			
			updateNetwork = false;
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
						if(id != 0 && !signalList.contains(id)) { signalList.add(id); }
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
		else
		{
			signalList.clear();
		}
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
	public boolean getSignalState(int id)
	{
		if(id == 0) { return false; }
		return signalList.contains(id);
	}
	
	@Override
	public Collection<ICBNetTile> getNetwork()
	{ return network; }
	
	@Override
	public void refreshNetwork()
	{ updateNetwork = true; }
}