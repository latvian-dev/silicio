package latmod.silicio.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import latmod.silicio.Silicio;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public abstract class BlockSil extends BlockLM
{
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	
	public BlockSil(Material m)
	{
		super(m);
		setCreativeTab(Silicio.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return Silicio.mod; }
}