package latmod.silicio.block;

import ftb.lib.api.block.BlockWithVariants;
import latmod.silicio.SilItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public enum VariantBlocks1 implements BlockWithVariants.IVariantEnum
{
	DENSE_SILICON(0, MapColor.blackColor),
	ELEMITE(1, MapColor.blueColor),
	SILICON_FRAME(2, MapColor.blackColor);
	
	public final String name;
	public final int meta;
	public final MapColor mapColor;
	
	VariantBlocks1(int id, MapColor c)
	{
		name = name().toLowerCase();
		meta = id;
		mapColor = c;
	}
	
	public String getName()
	{ return name; }
	
	public int getMetadata()
	{ return meta; }
	
	public MapColor getMapColor()
	{ return mapColor; }
	
	public ItemStack getStack(int q)
	{ return new ItemStack(SilItems.b_blocks_1, q, meta); }
}
