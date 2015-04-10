package latmod.silicio.item;

import latmod.core.LMMod;
import latmod.core.item.ItemLM;
import latmod.silicio.Silicio;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

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