package latmod.silicio.block;
import latmod.ftbu.tile.TileLM;
import latmod.silicio.tile.cb.TileModuleCopier;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockModuleCopier extends BlockSil
{
	public BlockModuleCopier(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		isBlockContainer = true;
		mod.addTile(TileModuleCopier.class, s);
	}
	
	public void loadRecipes()
	{
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileModuleCopier(); }
}