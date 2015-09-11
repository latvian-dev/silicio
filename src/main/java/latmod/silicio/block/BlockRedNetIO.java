package latmod.silicio.block;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.tile.TileLM;
import latmod.silicio.tile.cb.TileRedNetIO;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetOmniNode;
import powercrystals.minefactoryreloaded.api.rednet.connectivity.RedNetConnectionType;

public class BlockRedNetIO extends BlockSil implements IRedNetOmniNode
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_input;
	
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
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "rednet_io");
		icon_input = ir.registerIcon(mod.assets + "rednet_io_input");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		TileRedNetIO t = getTile(TileRedNetIO.class, iba, x, y, z);
		return (t != null && s == t.inputSide) ? icon_input : blockIcon;
	}
	
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