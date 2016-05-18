package latmod.silicio.item;

import com.feed_the_beast.ftbl.api.LangKey;
import latmod.silicio.api.SilCapabilities;
import latmod.silicio.api.modules.Module;
import latmod.silicio.modules.ModuleChatOutput;
import latmod.silicio.modules.ModuleTimer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemModule extends ItemSil
{
	public static final LangKey cbm_desc = new LangKey("silicio.item.cbm_desc");
	
	private Map<Integer, Module> moduleMap = new HashMap<>();
	private Map<Integer, String> moduleIDMap = new HashMap<>();
	
	private void register(int i, String id, Module m)
	{
		moduleMap.put(i, m);
		moduleIDMap.put(i, id);
	}
	
	public ItemModule()
	{
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
		
		register(0, "timer", new ModuleTimer());
		register(50, "chat_out", new ModuleChatOutput());
	}
	
	@Override
	public ICapabilityProvider initCapabilities(final ItemStack stack, final NBTTagCompound nbt)
	{
		return new ICapabilityProvider()
		{
			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing)
			{ return capability == SilCapabilities.MODULE && moduleMap.containsKey(stack.getMetadata()); }
			
			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing)
			{
				if(capability == SilCapabilities.MODULE)
				{
					return (T) moduleMap.get(stack.getMetadata());
				}
				
				return null;
			}
		};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void loadModels()
	{
		for(Map.Entry<Integer, String> e : moduleIDMap.entrySet())
		{
			ModelLoader.setCustomModelResourceLocation(this, e.getKey(), new ModelResourceLocation(getRegistryName(), "variant=" + e.getValue()));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for(Integer i : moduleMap.keySet())
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public void loadRecipes()
	{
	}
	
	@Override
	public String getUnlocalizedName(ItemStack is)
	{ return getMod().getItemName("cbm_" + moduleIDMap.get(is.getMetadata())); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
	{
		l.add(cbm_desc.translate());
	}
}
