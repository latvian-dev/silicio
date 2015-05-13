package latmod.silicio.block;
import latmod.core.tile.TileLM;
import latmod.silicio.tile.cb.TileRedNetIO;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetOmniNode;
import powercrystals.minefactoryreloaded.api.rednet.connectivity.RedNetConnectionType;

public class BlockRedNetIO extends BlockSil implements IRedNetOmniNode
{
	public BlockRedNetIO(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		isBlockContainer = true;
		mod.addTile(TileRedNetIO.class, s);
	}
	
	public void loadRecipes()
	{
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileRedNetIO(); }
	
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
		TileRedNetIO t = (TileRedNetIO)world.getTileEntity(x, y, z);
		if(t != null && !t.isInvalid()) t.onInputsChanged(side, inputValues);
	}
	
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{ onInputsChanged(world, x, y, z, side, new int[] { inputValue }); }
	
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{ return RedNetConnectionType.CableAll; }
	
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		TileRedNetIO t = (TileRedNetIO)world.getTileEntity(x, y, z);
		if(t != null && !t.isInvalid()) return t.getOutputValues(side);
		return new int[16];
	}
	
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{ return getOutputValues(world, x, y, z, side)[subnet]; }
}