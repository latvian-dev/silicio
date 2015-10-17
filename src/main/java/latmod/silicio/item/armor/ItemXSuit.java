package latmod.silicio.item.armor;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.api.item.IItemLM;
import latmod.latblocks.LatBlocks;
import latmod.silicio.Silicio;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;

public abstract class ItemXSuit extends ItemArmor implements IItemLM
{
	public final String itemName;
	
	public ItemXSuit(String s, int a)
	{
		super(ArmorMaterial.DIAMOND, 4, a);
		itemName = s;
	}
	
	public ItemXSuit register()
	{ LatBlocks.mod.addItem(this); return this; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return Silicio.mod.getItemName(itemName); }
	
	public void loadRecipes()
	{
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    { return Silicio.mod.assets + "textures/items/armor/xsuit_" + (slot == 3 ? "1" : "0") + ".png"; }
	
	public String getItemID()
	{ return itemName; }
	
	public void onPostLoaded()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		itemIcon = ir.registerIcon(Silicio.mod.assets + "armor/" + itemName);
	}
}