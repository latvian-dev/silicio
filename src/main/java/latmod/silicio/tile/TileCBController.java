package latmod.silicio.tile;

import ftb.lib.FTBLib;
import latmod.lib.IntList;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;
import latmod.silicio.api.tile.cb.ICBController;
import latmod.silicio.api.tile.cb.ICBModuleProvider;
import latmod.silicio.api.tile.energy.SilEnergyTank;
import latmod.silicio.block.BlockConnector;
import latmod.silicio.block.SilBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public class TileCBController extends TileCBNetwork implements ICBController
{
	public final SilEnergyTank energyTank;
	private final IntList signalList;
	private Collection<BlockPos> network;
	private Collection<TileEntity> connectedTileEntities;
	private List<ModuleContainer> modules;
	private boolean updateNetwork = true;
	
	public TileCBController()
	{
		energyTank = new SilEnergyTank(100000D);
		signalList = new IntList().setDefVal(0);
		network = new HashSet<>();
		connectedTileEntities = new ArrayList<>();
		modules = new ArrayList<>();
	}
	
	@Override
	public EnumSync getSync()
	{ return EnumSync.RERENDER; }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		signalList.clear();
		signalList.addAll(tag.getIntArray("Signals"));
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
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
		
		System.out.println(getWorld());
	}
	
	@Override
	public boolean onRightClick(EntityPlayer ep, ItemStack is, EnumFacing side, EnumHand hand, float x, float y, float z)
	{
		if(getSide().isServer())
		{
			FTBLib.printChat(ep, "Network: " + network.size());
			FTBLib.printChat(ep, "Modules: " + modules.size());
			FTBLib.printChat(ep, "Connected TEs: " + connectedTileEntities.size());
			FTBLib.printChat(ep, "Signals: " + signalList);
		}
		
		return true;
	}
	
	@Override
	public void onUpdate()
	{
		if(getSide().isClient())
		{
			return;
		}
		
		if(updateNetwork)
		{
			modules.clear();
			connectedTileEntities.clear();
			
			if(!network.isEmpty())
			{
				for(BlockPos bpos : network)
				{
					IBlockState state = worldObj.getBlockState(bpos);
					
					if(state.getBlock() == SilBlocks.CONNECTOR)
					{
						TileEntity te = worldObj.getTileEntity(bpos.offset(state.getValue(BlockConnector.FACING)));
						
						if(te != null)
						{
							connectedTileEntities.add(te);
						}
					}
					else
					{
						TileEntity te = worldObj.getTileEntity(bpos);
						
						if(te instanceof ICBModuleProvider)
						{
							ICBModuleProvider cbm = (ICBModuleProvider) te;
							
							if(cbm.hasModules())
							{
								modules.addAll(cbm.getModules());
							}
						}
					}
				}
			}
			else
			{
				signalList.clear();
			}
			
			updateNetwork = false;
		}
		
		IntList signalList0 = signalList.copy();
		signalList.clear();
		
		if(!modules.isEmpty())
		{
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
		}
		
		Map<Integer, Boolean> diffMap = signalList0.getDifferenceMap(signalList);
		
		if(!diffMap.isEmpty())
		{
			FTBLib.printChat(null, "Signals: " + diffMap);
		}
	}
	
	@Override
	public boolean getSignalState(int id)
	{ return id != 0 && signalList.contains(id); }
	
	@Override
	public void addToNetwork(BlockPos pos)
	{ network.add(pos); }
	
	@Override
	public Collection<BlockPos> getNetwork()
	{ return network; }
}