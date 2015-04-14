package latmod.silicio;

import latmod.core.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class SilMat
{
	public static ItemStack coverBlock = null;
	public static Block coverBlockB = null;
	
	public static ItemStack SILICON;
	public static ItemStack SILICON_DUST;
	public static ItemStack CIRCUIT;
	public static ItemStack LASER_CRYSTAL;
	
	public static void init()
	{
		updateCoverBlock();
	}

	public static void updateCoverBlock()
	{
		coverBlock = null;
		coverBlockB = null;
		
		if(SilConfig.General.coverBlock != null && !SilConfig.General.coverBlock.isEmpty())
		{
			coverBlock = InvUtils.singleCopy(InvUtils.parseItem(SilConfig.General.coverBlock));
			if(coverBlock != null) coverBlockB = Block.getBlockFromItem(coverBlock.getItem());
			else LatCoreMC.logger.warn("Block " + SilConfig.General.coverBlock + " not found!");
		}
	}
}