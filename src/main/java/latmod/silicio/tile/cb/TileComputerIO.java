package latmod.silicio.tile.cb;

import latmod.ftbu.core.util.IntList;
import latmod.silicio.tile.cb.events.*;
import net.minecraft.nbt.NBTTagCompound;
import dan200.computercraft.api.lua.*;
import dan200.computercraft.api.peripheral.*;

public class TileComputerIO extends TileBasicCBNetTile implements IPeripheral, IToggableTile, ISignalProviderTile // BlockComputerIO
{
	public IntList enabledChannels = new IntList();
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
		c.channels.addAll(enabledChannels);
	}
	
	public void onUpdateCB()
	{
	}
	
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
				int c = ((Number)arguments[0]).intValue() - 1;
				boolean b = ((Boolean)arguments[1]).booleanValue();
				
				if(!b) enabledChannels.removeValue(c);
				else if(!enabledChannels.contains(c))
					enabledChannels.add(c);
			}
		}
		else if(method == 1)
		{
			if(!getCBNetwork().hasWorkingController() || arguments == null || arguments.length < 1)
				return new Object[] { false };
			
			int c = ((Number)arguments[0]).intValue() - 1;
			return new Object[] { getCBNetwork().controller.channels.contains(c) };
		}
		
		return null;
	}
	
	public void attach(IComputerAccess computer)
	{ if(attachedComputer == null) attachedComputer = computer; }
	
	public void detach(IComputerAccess computer)
	{ if(attachedComputer != null && computer.equals(attachedComputer)) attachedComputer = null; }
	
	public boolean equals(IPeripheral other)
	{ return super.equals(other); }
	
	public void onChannelToggledTile(EventChannelToggledTile e)
	{
		if(getCBNetwork().hasWorkingController() && attachedComputer != null)
			attachedComputer.queueEvent("cb_channel", new Object[] { (e.channel + 1), e.on });
	}
	
	public void provideSignalsTile(EventProvideSignalsTile e)
	{
		for(int i = 0; i < enabledChannels.size(); i++)
			e.setEnabled0(enabledChannels.get(i));
	}
}