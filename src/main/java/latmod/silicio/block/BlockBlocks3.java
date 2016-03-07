package latmod.silicio.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockWithVariants;
import ftb.lib.api.item.ODItems;
import latmod.silicio.Silicio;
import latmod.silicio.item.ItemSilMaterials;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockBlocks3 extends BlockWithVariants<VariantBlocks3>
{
	public BlockBlocks3(String s)
	{ super(s, Material.rock); }
	
	public Class<VariantBlocks3> getVariantType()
	{ return VariantBlocks3.class; }
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(ODItems.GLASS, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(VariantBlocks3.SILICON_BLOCK.getStack(1), "SS", "SS", 'S', ItemSilMaterials.SILICON.getStack(1));
		getMod().recipes.addShapelessRecipe(ItemSilMaterials.SILICON.getStack(4), VariantBlocks3.SILICON_BLOCK.getStack(1));
		
		getMod().recipes.addSmelting(VariantBlocks3.SILICON_GLASS.getStack(1), VariantBlocks3.SILICON_BLOCK.getStack(1));
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{ return EnumWorldBlockLayer.TRANSLUCENT; }
	
	public boolean isOpaqueCube()
	{ return false; }
}