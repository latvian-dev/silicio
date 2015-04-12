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
	
	public final FastList<ModuleConfigSegment> moduleConfig = new FastList<ModuleConfigSegment>();
	
	public ItemModule(String s)
	{
		super("mod." + s);
		setMaxStackSize(8);
		setTextureName(s);
	}
	
	public int getChannelCount()
	{ return 0; }
	
	public IOType getModuleType()
	{ return IOType.NONE; }
	
	public IOType getChannelType(int c)
	{ return IOType.NONE; }
	
	public String getChannelName(int c)
	{ return (getChannelType(c).isInput() ? "Input" : "Output") + " #" + (c + 1); }
	
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
		l.add(StatCollector.translateToLocal(mod.assets + "item.mod.desc"));
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
	
	public void updateInvNet(ItemStack is, CircuitBoard t, FastList<InvEntry> list) { }
	public void updateTankNet(ItemStack is, CircuitBoard t, FastList<TankEntry> list) { }
	
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
	
	public final CBChannel getChannel(ItemStack is, CircuitBoard t, int c)
	{
		int ch = getChannelID(this, is, c);
		if(ch < 0) return CBChannel.NONE;
		if(ch < 16) return t.cable.channels[ch];
		if(t.cable.controller == null) return CBChannel.NONE;
		return t.cable.controller.channels[ch - 16];
	}
	
	public final void setChannel(ItemStack is, CircuitBoard t, int c, byte ch)
	{
		getChannel(is, t, c);
		byte[] channels = is.stackTagCompound.getByteArray(NBT_TAG);
		channels[c] = ch;
		is.stackTagCompound.setByteArray(NBT_TAG, channels);
	}
}