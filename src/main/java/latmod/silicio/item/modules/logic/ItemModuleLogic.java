package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.ItemModule;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.*;

public abstract class ItemModuleLogic extends ItemModule
{
	public ItemModuleLogic(String s)
	{
		super(s);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{ itemIcon = ir.registerIcon(mod.assets + "modules/logic/" + itemName.substring(4)); }
}