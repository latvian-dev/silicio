package latmod.silicio.item;

import latmod.silicio.Silicio;
import latmod.silicio.api.modules.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemModule extends ItemSil implements IModuleItem
{
	public final Module module;
	
	public ItemModule(Module m)
	{
		super("module_" + m.getID());
		module = m;
		setMaxStackSize(1);
	}
	
	public void loadModels()
	{
		module.loadModels(this);
	}
	
	public void loadRecipes()
	{
		module.loadRecipes();
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return getMod().getItemName("cbm_" + module.getID()); }
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
	{
		l.add(Silicio.mod.translate("item.cbm_desc"));
		module.addInformation(is, ep, l);
	}
	
	public Module getModule(ItemStack item)
	{ return module; }
}
