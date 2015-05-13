package latmod.silicio.item.modules.io;

import latmod.silicio.item.modules.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.*;

public abstract class ItemModuleIO extends ItemModule
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_input, icon_output;
	
	public ItemModuleIO(String s)
	{ super(s); }
	
	public int getRenderPasses(int m)
	{ return (getModuleType() == IOType.NONE) ? 1 : 2; }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(mod.assets + "modules/io/" + iconString);
		icon_input = ir.registerIcon(mod.assets + "modules/io/input");
		icon_output = ir.registerIcon(mod.assets + "modules/io/output");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{
		if(r == 1)
		{
			if(getModuleType() == IOType.OUTPUT) return icon_output;
			else if(getModuleType() == IOType.INPUT) return icon_input;
		}
		
		return itemIcon;
	}
}