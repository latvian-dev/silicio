package latmod.silicio.block;

import latmod.core.LMMod;
import latmod.core.block.BlockLM;
import latmod.silicio.Silicio;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public abstract class BlockSil extends BlockLM
{
	public BlockSil(String s, Material m)
	{ super(s, m); }
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return Silicio.tab; }
}