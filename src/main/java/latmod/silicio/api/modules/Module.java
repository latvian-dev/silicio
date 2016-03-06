package latmod.silicio.api.modules;

import ftb.lib.mod.FTBLibMod;
import latmod.lib.*;
import latmod.lib.util.FinalIDObject;
import latmod.silicio.item.ItemModule;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public abstract class Module extends FinalIDObject
{
	public static final byte FLAG_PROVIDE_SIGNALS = 0;
	
	private byte flags = 0;
	
	public Module(String id)
	{
		super(id);
	}
	
	protected void setFlag(byte flag, boolean b)
	{ flags = Bits.setBit(flags, flag, b); }
	
	public boolean getFlag(byte flag)
	{ return Bits.getBit(flags, flag); }
	
	public void init(ModuleContainer c)
	{
	}
	
	public void loadRecipes()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void loadModels(ItemModule item)
	{
		FTBLibMod.proxy.addItemModel(item.getMod().getID(), item, 0, "modules", "variant=" + getID());
	}
	
	public void onAdded(ModuleContainer c, EntityPlayerMP player)
	{
	}
	
	public void onRemoved(ModuleContainer c, EntityPlayerMP player)
	{
	}
	
	public void onUpdate(ModuleContainer c)
	{
	}
	
	public void onSignalChanged(ModuleContainer c, int id, boolean on)
	{
	}
	
	public void provideSignals(ModuleContainer c, IntList list)
	{
		list.add(c.getConnectionID(null));
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List<String> l)
	{
	}
}
