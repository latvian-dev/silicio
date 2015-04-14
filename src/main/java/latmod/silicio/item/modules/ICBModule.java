package latmod.silicio.item.modules;

import latmod.core.util.FastList;
import latmod.silicio.item.modules.config.ModuleConfigSegment;
import latmod.silicio.tile.*;

public interface ICBModule // ItemModule
{
	public void onUpdate(CircuitBoard cb, int MID);
	public void updateInvNet(CircuitBoard cb, int MID, FastList<InvEntry> list);
	public void updateTankNet(CircuitBoard cb, int MID, FastList<TankEntry> list);
	public CBChannel getChannel(CircuitBoard cb, int MID, int c);
	public void setChannel(CircuitBoard cb, int MID, int c, byte ch);
	public int getChannelCount();
	public IOType getModuleType();
	public IOType getChannelType(int c);
	public String getChannelName(int c);
	public FastList<ModuleConfigSegment> getModuleConfig();
}