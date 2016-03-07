package latmod.silicio.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockWithVariants;
import latmod.silicio.*;
import latmod.silicio.item.ItemSilMaterials;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockBlocks1 extends BlockWithVariants<VariantBlocks1>
{
	public BlockBlocks1(String s)
	{ super(s, Material.rock); }
	
	public Class<VariantBlocks1> getVariantType()
	{ return VariantBlocks1.class; }
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(VariantBlocks1.ELEMITE.getStack(1), "III", "III", "III", 'I', ItemSilMaterials.ELEMITE_INGOT.getStack(1));
		getMod().recipes.addShapelessRecipe(ItemSilMaterials.ELEMITE_INGOT.getStack(9), VariantBlocks1.ELEMITE.getStack(1));
		getMod().recipes.addSmelting(VariantBlocks1.ELEMITE.getStack(1), new ItemStack(SilItems.b_blue_goo));
		
		getMod().recipes.addRecipe(VariantBlocks1.DENSE_SILICON.getStack(1), "III", "III", "III", 'I', VariantBlocks3.SILICON_BLOCK.getStack(1));
		getMod().recipes.addShapelessRecipe(VariantBlocks3.SILICON_BLOCK.getStack(9), VariantBlocks1.DENSE_SILICON.getStack(1));
	}
}