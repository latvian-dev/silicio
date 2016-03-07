package latmod.silicio.block;

import ftb.lib.api.block.BlockWithVariants;
import latmod.silicio.SilItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public enum VariantBlocks3 implements BlockWithVariants.IVariantEnum
{
	SILICON_BLOCK(0, MapColor.grayColor),
	SILICON_GLASS(1, MapColor.silverColor);
	
	public final String name;
	public final int meta;
	public final MapColor mapColor;
	
	VariantBlocks3(int id, MapColor c)
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
	{ return new ItemStack(SilItems.b_blocks_3, q, meta); }
}
