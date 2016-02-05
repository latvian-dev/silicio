package latmod.silicio.block;

import ftb.lib.api.tile.TileLM;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSiliconBlock extends BlockSil
{
	public BlockSiliconBlock(String s)
	{ super(s, Material.rock); }
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.TRANSLUCENT; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
}