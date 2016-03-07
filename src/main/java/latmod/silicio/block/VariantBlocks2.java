package latmod.silicio.block;

import ftb.lib.api.block.BlockWithVariants;
import latmod.silicio.SilItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 07.03.2016.
 */
public enum VariantBlocks2 implements BlockWithVariants.IVariantEnum
{
	SILICON_FRAME(0, MapColor.silverColor),
	REACTOR_CORE(1, MapColor.grayColor);
	
	public final String name;
	public final int meta;
	public final MapColor mapColor;
	
	VariantBlocks2(int id, MapColor c)
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
	{ return new ItemStack(SilItems.b_blocks_2, q, meta); }
}
