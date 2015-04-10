package latmod.silicio.item.modules;

import latmod.core.util.FastList;
import latmod.silicio.item.modules.config.ModuleConfigSegment;
import latmod.silicio.tile.*;
import net.minecraft.item.ItemStack;

public interface ICBModule // ItemModule
{
	public void onUpdate(ItemStack is, CircuitBoard t);
	public void updateInvNet(ItemStack is, CircuitBoard t, FastList<InvEntry> list);
	public void updateTankNet(ItemStack is, CircuitBoard t, FastList<TankEntry> list);
	public CBChannel getChannel(ItemStack is, CircuitBoard t, int c);
	public void setChannel(ItemStack is, CircuitBoard t, int c, byte ch);
	public int getChannelCount();
	public IOType getModuleType();
	public IOType getChannelType(int c);
	public String getChannelName(int c);
	public FastList<ModuleConfigSegment<?>> getModuleConfig();
}