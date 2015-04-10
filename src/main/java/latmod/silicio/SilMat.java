package latmod.silicio;

import latmod.core.InvUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cofh.thermalexpansion.block.simple.BlockFrame;

public class SilMat
{
	public static ItemStack coverBlock = null;
	public static Block coverBlockB = null;
	
	public static ItemStack SILICON;
	public static ItemStack SILICON_DUST;
	public static ItemStack CIRCUIT;
	public static ItemStack LASER_CRYSTAL;
	
	public static ItemStack FRAME[];
	public static ItemStack FRAME_TESSERACT;
	
	public static void init()
	{
		FRAME = new ItemStack[]
		{
				BlockFrame.frameMachineBasic,
				BlockFrame.frameMachineHardened,
				BlockFrame.frameMachineReinforced,
				BlockFrame.frameMachineResonant,
		};
		
		FRAME_TESSERACT = BlockFrame.frameTesseractFull;
		
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
		}
	}
}