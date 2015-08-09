package latmod.silicio;

import latmod.latblocks.LatBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class SilMat
{
	public static ItemStack coverBlock = null;
	public static Block coverBlockB = null;
	
	public static Object SILICON;
	public static ItemStack SILICON_ITEM;
	public static ItemStack SILICON_DUST;
	public static ItemStack CIRCUIT;
	public static ItemStack LAPIS_CRYSTAL;
	public static ItemStack REDSTONE_CRYSTAL;
	
	public static void init()
	{
		coverBlockB = LatBlocksItems.b_paintable;
		coverBlock = new ItemStack(coverBlockB, 1);
	}
}