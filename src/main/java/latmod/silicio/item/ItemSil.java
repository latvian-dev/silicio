package latmod.silicio.item;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.item.ItemLM;
import latmod.ftbu.util.LMMod;
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