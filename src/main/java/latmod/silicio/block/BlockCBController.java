package latmod.silicio.block;
import latmod.core.tile.TileLM;
import latmod.silicio.tile.TileCBController;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockCBController extends BlockSil
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_conflict;
	
	public BlockCBController(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		isBlockContainer = true;
		mod.addTile(TileCBController.class, s);
	}
	
	public void loadRecipes()
	{
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileCBController(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "controller");
		icon_conflict = ir.registerIcon(mod.assets + "controller_conflict");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		TileCBController t = (TileCBController)iba.getTileEntity(x, y, z);
		if(t != null && t.isValid() && t.hasConflict)
			return icon_conflict;
		return blockIcon;
	}
}