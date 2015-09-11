package latmod.silicio.block;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.LMMod;
import latmod.ftbu.core.block.BlockLM;
import latmod.silicio.Silicio;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

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