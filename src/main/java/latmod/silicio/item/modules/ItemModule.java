package latmod.silicio.item.modules;
import latmod.core.LatCoreMC;
import latmod.core.util.FastList;
import latmod.silicio.item.ItemSil;
import latmod.silicio.item.modules.config.ModuleConfigSegment;
import latmod.silicio.item.modules.io.ItemModuleIO;
import latmod.silicio.item.modules.logic.ItemModuleLogic;
import latmod.silicio.tile.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public abstract class ItemModule extends ItemSil implements ICBModule
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
	
	public final FastList<ModuleConfigSegment> getModuleConfig()
	{ return moduleConfig; }
	
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
	
	public void updateInvNet(CircuitBoard cb, int MID, FastList<InvEntry> list) { }
	public void updateTankNet(CircuitBoard cb, int MID, FastList<TankEntry> list) { }
	
	public static final int getChannelID(ICBModule m, ItemStack is, int c)
	{
		if(m.getChannelCount() <= 0) return 0;
		
		if(!is.hasTagCompound())
			is.stackTagCompound = new NBTTagCompound();
		
		byte[] channels = is.stackTagCompound.getByteArray(NBT_TAG);
		
		if(channels.length == 0)
		{
			channels = new byte[m.getChannelCount()];
			for(int i = 0; i < channels.length; i++)
				channels[i] = -1;
			
			is.stackTagCompound.setByteArray(NBT_TAG, channels);
		}
		
		return channels[c];
	}
	
	public final CBChannel getChannel(CircuitBoard cb, int MID, int c)
	{
		int ch = getChannelID(this, cb.items[MID], c);
		if(ch < 0) return CBChannel.NONE;
		if(ch < 16) return cb.cable.channels[ch];
		if(cb.cable.controller == null) return CBChannel.NONE;
		return cb.cable.controller.channels[ch - 16];
	}
	
	public final void setChannel(CircuitBoard cb, int MID, int c, byte ch)
	{
		getChannel(cb, MID, c);
		byte[] channels = cb.items[MID].stackTagCompound.getByteArray(NBT_TAG);
		channels[c] = ch;
		cb.items[MID].stackTagCompound.setByteArray(NBT_TAG, channels);
	}
}