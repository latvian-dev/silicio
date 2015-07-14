package latmod.silicio.tile.cb;

import latmod.ftbu.core.util.*;
import latmod.silicio.tile.cb.events.EventCB;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class CBNetwork
{
	public TileCBController controller = null;
	public final FastList<ICBNetTile> tiles;
	public final FastList<CircuitBoard> circuitBoards;
	public final FastList<ModuleEntry> allModules;
	public final FastList<InvEntry> invNetwork;
	public final FastList<TankEntry> tankNetwork;
	public boolean hasConflict;
	
	public CBNetwork()
	{
		tiles = new FastList<ICBNetTile>();
		circuitBoards = new FastList<CircuitBoard>();
		allModules = new FastList<ModuleEntry>();
		invNetwork = new FastList<InvEntry>();
		tankNetwork = new FastList<TankEntry>();
	}
	
	public boolean hasController()
	{ return controller != null && !controller.isInvalid(); }
	
	public boolean hasConflict()
	{ return hasController() && hasConflict; }
	
	public boolean hasWorkingController()
	{ return hasController() && !hasConflict; }
	
	public void notifyNetwork()
	{
		if(hasController())
		{
			
		}
		else
		{
		}
	}
	
	public void sendEvent(EventCB e)
	{
		for(int i = 0; i < tiles.size(); i++)
			tiles.get(i).onCBNetworkEvent(e);
	}
	
	/*
	public void test()
	{
		if(ec instanceof TileCBCable)
		{
			TileCBCable tc = (TileCBCable)ec;
			for(int b = 0; b < 6; b++) if(tc.boards[b] != null)
			{
				circuitBoards.add(tc.boards[b]);
				
				for(int mid = 0; mid < tc.boards[b].items.length; mid++)
				{
					if(tc.boards[b].items[mid] != null && tc.boards[b].items[mid].getItem() instanceof ItemModule)
					{
						ModuleEntry me = new ModuleEntry(getCBNetwork(), tc.boards[b], mid);
						allModules.add(me);
						
						if(me.item instanceof IInvProvider) ((IInvProvider)me.item).updateInvNet(me, invNetwork);
						if(me.item instanceof ITankProvider) ((ITankProvider)me.item).updateTankNet(me, tankNetwork);
					}
				}
			}
		}
	}
	*/
	
	// Static //
	
	private static final IntList tempIntList = new IntList();
	
	public static ICBNetTile getNetTile(World w, int x, int y, int z)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		return (te != null && te instanceof ICBNetTile) ? (ICBNetTile)te : null;
	}
	
	public static CBNetwork create(World w, int x, int y, int z)
	{
		ICBNetTile t0 = getNetTile(w, x, y, z);
		
		if(t0 != null && t0.getCBNetwork().hasController())
			return t0.getCBNetwork().controller.getCBNetwork();
		
		CBNetwork net = new CBNetwork();
		update(net, w, x, y, z);
		return net;
	}
	
	public static void update(CBNetwork net, World w, int x, int y, int z)
	{
		tempIntList.clear();
		net.circuitBoards.clear();
		net.allModules.clear();
		
		addToList(net, w, x, y, z);
		net.updateOtherNetworks();
		
		net.controller = net.tiles.isEmpty() ? null : (TileCBController)net.tiles.get(tempIntList.get(0));
		if(tempIntList.size() > 1) net.hasConflict = true;
		net.tiles.removeAll(tempIntList.toArray());
	}
	
	public void updateOtherNetworks()
	{
		invNetwork.clear();
		tankNetwork.clear();
		
		invNetwork.sort(null);
		tankNetwork.sort(null);
	}
	
	private static void addToList(CBNetwork net, World w, int x, int y, int z)
	{
		for(int i = 0; i < 6; i++)
		{
			int px = x + Facing.offsetsXForSide[i];
			int py = y + Facing.offsetsYForSide[i];
			int pz = z + Facing.offsetsZForSide[i];
			
			TileEntity te = w.getTileEntity(px, py, pz);
			
			if(te != null && te instanceof ICBNetTile)
			{
				if(te instanceof TileCBController)
					tempIntList.add(net.tiles.size());
				
				ICBNetTile ec = (ICBNetTile)te;
				
				if(ec.isSideEnabled(Facing.oppositeSide[i]) && !net.tiles.contains(ec))
				{
					net.tiles.add(ec);
					addToList(net, w, px, py, pz);
				}
			}
		}
	}
	
	public static void notifyNetwork(World w, int x, int y, int z)
	{ create(w, x, y, z).notifyNetwork(); }
}