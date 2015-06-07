package latmod.silicio.item;
import latmod.ftbu.core.ODItems;
import latmod.ftbu.core.client.LatCoreMCClient;
import latmod.silicio.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.*;

public class ItemMaterialsSil extends ItemSil
{
	public static final String[] names =
	{
		"module_empty",
		"module_input",
		"module_output",
		"silicon_gem",
		"silicon_dust",
		"circuit",
		"laser_crystal",
		"module_logic",
	};
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public ItemMaterialsSil(String s)
	{ super(s); }
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
		
		SilItems.Modules.EMPTY = new ItemStack(this, 1, 0);
		SilItems.Modules.INPUT = new ItemStack(this, 1, 1);
		SilItems.Modules.OUTPUT = new ItemStack(this, 1, 2);
		SilMat.SILICON = new ItemStack(this, 1, 3);
		SilMat.SILICON_DUST = new ItemStack(this, 1, 4);
		SilMat.CIRCUIT = new ItemStack(this, 1, 5);
		SilMat.LASER_CRYSTAL = new ItemStack(this, 1, 6);
		SilItems.Modules.LOGIC = new ItemStack(this, 1, 7);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addSmelting(SilMat.SILICON, SilMat.SILICON_DUST);
		
		mod.recipes.addRecipe(SilMat.CIRCUIT, "CCC", "ISI", "CCC",
				'C', SilItems.b_cbcable,
				'S', SilMat.SILICON,
				'I', ODItems.IRON);
		
		mod.recipes.addShapelessRecipe(SilItems.Modules.LOGIC, SilItems.Modules.EMPTY, SilMat.SILICON, ODItems.QUARTZ, ODItems.REDSTONE);
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return mod.getItemName("mat." + names[is.getItemDamage()]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < names.length; i++)
			icons[i] = ir.registerIcon(mod.assets + "mat/" + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int m, int r)
	{ return (m >= 0 && m < icons.length) ? icons[m] : LatCoreMCClient.unknownItemIcon; }
}