package latmod.silicio.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.silicio.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
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
		SilRecipes.addSmelter(SilItems.Modules.EMPTY, SilMat.CIRCUIT, new ItemStack(Items.iron_ingot, 8), 48000);
		
		SilRecipes.addSmelter(SilItems.Modules.INPUT, SilItems.Modules.EMPTY, new ItemStack(Blocks.hopper), 1200);
		SilRecipes.addSmelter(SilItems.Modules.OUTPUT, SilItems.Modules.EMPTY, new ItemStack(Blocks.dropper), 1200);
		
		SilRecipes.addPulverizer(SilMat.SILICON_DUST, SilMat.SILICON, 2400);
		SilRecipes.addPulverizer(SilMat.SILICON_DUST, Blocks.sand, 4800);
		mod.recipes.addSmelting(SilMat.SILICON, SilMat.SILICON_DUST);
		
		mod.recipes.addRecipe(SilMat.CIRCUIT, "CCC", "ISI", "CCC",
				'C', SilItems.b_cbcable,
				'S', SilMat.SILICON,
				'I', ODItems.IRON);
		
		SilRecipes.addSmelter(SilMat.LASER_CRYSTAL, new ItemStack(Items.dye, 1, 4), new ItemStack(Blocks.glass, 4), 48000);
	}
}