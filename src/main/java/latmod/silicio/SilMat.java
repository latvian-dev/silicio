package latmod.silicio;

import latmod.ftbu.core.LatCoreMC;
import latmod.ftbu.core.inv.LMInvUtils;
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
		updateCoverBlock();
	}

	public static void updateCoverBlock()
	{
		coverBlock = null;
		coverBlockB = null;
		
		if(SilConfig.General.coverBlock != null && !SilConfig.General.coverBlock.isEmpty())
		{
			coverBlock = LMInvUtils.singleCopy(LMInvUtils.parseItem(SilConfig.General.coverBlock));
			if(coverBlock != null) coverBlockB = Block.getBlockFromItem(coverBlock.getItem());
			else LatCoreMC.logger.warn("Block " + SilConfig.General.coverBlock + " not found!");
		}
	}
}