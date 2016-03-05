package latmod.silicio.item;

import ftb.lib.mod.FTBLibMod;
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
	public final IModule module;
	
	public ItemModule(IModule m)
	{
		super("module_" + m.getID());
		module = m;
		setMaxStackSize(1);
	}
	
	public void loadModels()
	{
		FTBLibMod.proxy.addItemModel(getMod().getID(), getItem(), 0, "modules/" + module.getID());
	}
	
	public void loadRecipes()
	{
		module.loadRecipes();
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return getMod().getItemName("module." + module.getID()); }
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
	{
		l.add("Socket Module");
	}
	
	public IModule getModule(ItemStack item)
	{ return module; }
}
