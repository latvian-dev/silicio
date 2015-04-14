package latmod.silicio.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.silicio.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemMaterialsSil extends ItemMaterials
{
	public ItemMaterialsSil(String s)
	{ super(s); }
	
	public String[] getNames()
	{
		return new String[]
		{
			"emptyModule",
			"inputModule",
			"outputModule",
			"gemSilicon",
			"dustSilicon",
			"circuit",
			"laserCrystal"
		};
	}
	
	public String getPrefix()
	{ return "mat"; }
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	public CreativeTabs getCreativeTab()
	{ return Silicio.tab; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		SilItems.Modules.EMPTY = new ItemStack(this, 1, 0);
		SilItems.Modules.INPUT = new ItemStack(this, 1, 1);
		SilItems.Modules.OUTPUT = new ItemStack(this, 1, 2);
		SilMat.SILICON = new ItemStack(this, 1, 3);
		SilMat.SILICON_DUST = new ItemStack(this, 1, 4);
		SilMat.CIRCUIT = new ItemStack(this, 1, 5);
		SilMat.LASER_CRYSTAL = new ItemStack(this, 1, 6);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addSmelting(SilMat.SILICON, SilMat.SILICON_DUST);
		
		mod.recipes.addRecipe(SilMat.CIRCUIT, "CCC", "ISI", "CCC",
				'C', SilItems.b_cbcable,
				'S', SilMat.SILICON,
				'I', ODItems.IRON);
	}
}