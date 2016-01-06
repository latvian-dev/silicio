package latmod.silicio.block;
import latmod.ftbu.tile.TileLM;
import latmod.silicio.tile.cb.TileComputerIO;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockComputerIO extends BlockSil
{
	public BlockComputerIO(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		isBlockContainer = true;
		getMod().addTile(TileComputerIO.class, s);
	}
	
	public void loadRecipes()
	{
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileComputerIO(); }
}