package latmod.silicio.item.modules;
import latmod.core.*;
import latmod.core.util.*;
import latmod.silicio.item.ItemSil;
import latmod.silicio.item.modules.config.ModuleConfigSegment;
import latmod.silicio.item.modules.events.EventUpdateModule;
import latmod.silicio.item.modules.io.ItemModuleIO;
import latmod.silicio.item.modules.logic.ItemModuleLogic;
import latmod.silicio.tile.cb.CircuitBoard;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public abstract class ItemModule extends ItemSil
{
	public final boolean isIOModule = (this instanceof ItemModuleIO);
	public final boolean isLogicModule = (this instanceof ItemModuleLogic);
	
	public static final String NBT_TAG = "Channels";
	
	public final FastList<ModuleConfigSegment> moduleConfig;
	protected final String[] channelNames;
	
	public ItemModule(String s)
	{
		super("cbm_" + s);
		setMaxStackSize(8);
		setTextureName(s);
		
		moduleConfig = new FastList<ModuleConfigSegment>();
		channelNames = new String[getChannelCount()];
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
	public String getChannelName(int c)
	{ if(channelNames[c] != null) return channelNames[c];
	return "#" + (c + 1) + (getChannelType(c).isInput() ? " [Input]" : " [Output]"); }
	
	public boolean isItemTool(ItemStack is)
	{ return is.stackTagCompound != null; }
	
	public abstract void loadRecipes();
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{ itemIcon = ir.registerIcon(mod.assets + "modules/" + itemName.substring(4)); }
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		l.add(StatCollector.translateToLocal(mod.assets + "item.cbm_desc"));
		if(is.stackTagCompound != null)
		{
			l.add("Preconfigured");
			
			/*if(is.stackTagCompound.hasKey(NBT_TAG))
			{
			}*/
		}
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && ep.isSneaking() && is.stackTagCompound != null)
		{
			is.stackTagCompound = null;
			LatCoreMC.printChat(ep, "Configuration cleared");
		}
		
		return is;
	}
	
	public void onUpdate(EventUpdateModule e)
	{
	}
	
	public final void onUpdate(CircuitBoard cb, int MID)
	{
	}
	
	public static final int getChannelID(ItemModule m, ItemStack is, int c)
	{
		if(m.getChannelCount() <= 0) return 0;
		
		if(!is.hasTagCompound())
			is.stackTagCompound = new NBTTagCompound();
		
		if(is.stackTagCompound.func_150299_b(NBT_TAG) == NBTHelper.BYTE_ARRAY)
		{
			byte[] b = is.stackTagCompound.getByteArray(NBT_TAG);
			is.stackTagCompound.setIntArray(NBT_TAG, Converter.toInts(b));
		}
		
		int[] channels = is.stackTagCompound.getIntArray(NBT_TAG);
		
		if(channels.length == 0)
		{
			channels = new int[m.getChannelCount()];
			for(int i = 0; i < channels.length; i++)
				channels[i] = -1;
			
			is.stackTagCompound.setIntArray(NBT_TAG, channels);
		}
		
		return channels[c];
	}
	
	public final int getChannel(CircuitBoard cb, int MID, int id)
	{
		int ch = getChannelID(this, cb.items[MID], id);
		if(ch < 0 || cb.cable.controller == null || ch >= cb.cable.controller.channels.size()) return -1;
		return cb.cable.controller.channels.get(ch);
	}
	
	public final void setChannel(CircuitBoard cb, int MID, int id, int ch)
	{
		getChannel(cb, MID, id);
		int[] channels = cb.items[MID].stackTagCompound.getIntArray(NBT_TAG);
		channels[id] = ch;
		cb.items[MID].stackTagCompound.setIntArray(NBT_TAG, channels);
	}
}