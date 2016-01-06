package latmod.silicio.item.modules.logic;

import cpw.mods.fml.relauncher.*;
import latmod.silicio.item.modules.ItemModule;
import net.minecraft.client.renderer.texture.IIconRegister;

public abstract class ItemModuleLogic extends ItemModule
{
	public ItemModuleLogic(String s)
	{
		super(s);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{ itemIcon = ir.registerIcon(getMod().assets + "modules/logic/" + itemName.substring(4)); }
}