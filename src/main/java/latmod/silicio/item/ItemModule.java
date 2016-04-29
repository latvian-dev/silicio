package latmod.silicio.item;

import ftb.lib.api.LangKey;
import latmod.silicio.api.modules.IModuleItem;
import latmod.silicio.api.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemModule extends ItemSil implements IModuleItem
{
	public static final LangKey cbm_desc = new LangKey("silicio.item.cbm_desc");
	
	public final Module module;
	
	public ItemModule(Module m)
	{
		module = m;
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void loadModels()
	{
		module.loadModels(this);
	}
	
	@Override
	public void loadRecipes()
	{
		module.loadRecipes();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack is)
	{ return getMod().getItemName("cbm_" + module.getID()); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
	{
		l.add(cbm_desc.format());
		module.addInformation(is, ep, l);
	}
	
	@Override
	public Module getModule(ItemStack item)
	{ return module; }
}
