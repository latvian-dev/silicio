package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.ItemModule;
import latmod.silicio.tile.CircuitBoard;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.*;

public abstract class ItemModuleLogic extends ItemModule
{
	public ItemModuleLogic(String s)
	{
		super(s);
	}
	
	public void onUpdate(ItemStack is, CircuitBoard t)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{ itemIcon = ir.registerIcon(mod.assets + "modules/logic/" + itemName.substring(4)); }
}