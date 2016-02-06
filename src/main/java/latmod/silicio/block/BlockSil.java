package latmod.silicio.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import latmod.silicio.Silicio;
import net.minecraft.block.material.Material;

public abstract class BlockSil extends BlockLM
{
	public BlockSil(String s, Material m)
	{
		super(s, m);
		setCreativeTab(Silicio.tab);
	}
	
	public LMMod getMod()
	{ return Silicio.mod; }
}