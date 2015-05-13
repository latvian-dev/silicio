package latmod.silicio.tile.cb;

import latmod.core.tile.TileLM;
import latmod.core.util.IntList;
import net.minecraft.nbt.NBTTagCompound;
import dan200.computercraft.api.lua.*;
import dan200.computercraft.api.peripheral.*;

public class TileComputerIO extends TileLM implements ICBNetTile, IPeripheral // BlockComputerIO
{
	public IntList enabledChannels = new IntList();
	private TileCBController controller = null;
	private IComputerAccess attachedComputer = null;
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		enabledChannels.clear();
		enabledChannels.addAll(tag.getIntArray("Enabled"));
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setIntArray("Enabled", enabledChannels.toArray());
	}
	
	public void preUpdate(TileCBController c)
	{
		controller = c;
		c.channels.addAll(enabledChannels);
	}
	
	public void onUpdateCB()
	{
		if(controller != null && attachedComputer != null) for(int i = 0; i < controller.channelChanges.size(); i++)
			attachedComputer.queueEvent("cb_channel", new Object[] { (controller.channelChanges.keys.get(i).intValue() + 1), controller.channelChanges.values.get(i).booleanValue() });
	}
	
	public void onControllerDisconnected()
	{ controller = null; }
	
	public boolean isSideEnabled(int side)
	{ return true; }
	
	public String getType()
	{ return "cb_io"; }
	
	public String[] getMethodNames()
	{ return new String[] { "setChannel", "getChannel" }; }
	
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException
	{
		if(method == 0)
		{
			if(arguments != null && arguments.length == 2)
			{
				int c = ((Number)arguments[0]).intValue();
				boolean b = ((Boolean)arguments[1]).booleanValue();
				
				if(!b) enabledChannels.removeValue(c);
				else if(!enabledChannels.contains(c))
					enabledChannels.add(c);
			}
		}
		else if(method == 1)
		{
			if(controller == null || arguments == null || arguments.length < 1)
				return new Object[] { 0 };
			
			int c = ((Number)arguments[0]).intValue();
			if(c < 1 || c > controller.channels.size())
				return new Object[] { 0 };
			
			return new Object[] { controller.channels.contains(c - 1) };
		}
		
		return null;
	}
	
	public void attach(IComputerAccess computer)
	{ if(attachedComputer == null) attachedComputer = computer; }
	
	public void detach(IComputerAccess computer)
	{ if(attachedComputer != null && computer.equals(attachedComputer)) attachedComputer = null; }
	
	public boolean equals(IPeripheral other)
	{ return super.equals(other); }
}