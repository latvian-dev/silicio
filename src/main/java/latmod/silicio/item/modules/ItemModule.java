package latmod.silicio.item.modules;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.util.LatCoreMC;
import latmod.lib.*;
import latmod.silicio.item.ItemSil;
import latmod.silicio.item.modules.config.ModuleConfigSegment;
import latmod.silicio.item.modules.io.ItemModuleIO;
import latmod.silicio.item.modules.logic.ItemModuleLogic;
import latmod.silicio.tile.cb.CircuitBoard;
import latmod.silicio.tile.cb.events.EventUpdateModule;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class ItemModule extends ItemSil
{
	public final boolean isIOModule = (this instanceof ItemModuleIO);
	public final boolean isLogicModule = (this instanceof ItemModuleLogic);
	
	public static final String NBT_TAG = "CMap";
	
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
		if(hasData(is))
		{
			l.add("Preconfigured");
			IntMap m = getChannelMap(is);
			
			for(int i = 0; i < m.size(); i++)
				l.add(m.keys.get(i) + ": " + LMColorUtils.getHex(m.values.get(i)));
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
	
	public static final boolean hasData(ItemStack is)
	{ return is != null && is.hasTagCompound() && is.getTagCompound().hasKey(NBT_TAG); }
	
	public static final IntMap getChannelMap(ItemStack is)
	{ return hasData(is) ? IntMap.fromIntArrayS(is.getTagCompound().getIntArray(NBT_TAG)) : new IntMap(0); }
	
	public static final void setChannelMap(ItemStack is, IntMap map)
	{
		if(is == null || map == null) return;
		if(!is.hasTagCompound()) is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setIntArray(NBT_TAG, map.toIntArray());
	}
	
	public static final int getChannel(ItemStack is, int id)
	{ return getChannelMap(is).get(id); }
	
	public static final void setChannel(ItemStack is, int id, int col)
	{
		IntMap map = getChannelMap(is);
		map.put(id, col);
		setChannelMap(is, map);
	}
}