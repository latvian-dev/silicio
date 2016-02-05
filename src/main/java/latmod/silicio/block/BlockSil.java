package latmod.silicio.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import latmod.silicio.Silicio;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.*;

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