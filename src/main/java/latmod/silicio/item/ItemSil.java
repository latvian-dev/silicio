package latmod.silicio.item;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.LMMod;
import latmod.ftbu.core.item.ItemLM;
import latmod.silicio.Silicio;
import net.minecraft.creativetab.CreativeTabs;

public class ItemSil extends ItemLM
{
	public ItemSil(String s)
	{ super(s); }
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return Silicio.tab; }
}