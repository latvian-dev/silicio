package latmod.silicio.block;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.*;
import ftb.lib.OtherMods;
import latmod.ftbu.tile.TileLM;
import latmod.silicio.tile.cb.TileRedNetIO;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "powercrystals.minefactoryreloaded.api.rednet.IRedNetOmniNode", modid = OtherMods.MFR)
public class BlockRedNetIO extends BlockSil implements powercrystals.minefactoryreloaded.api.rednet.IRedNetOmniNode
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_input;
	
	public BlockRedNetIO(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		isBlockContainer = true;
		getMod().addTile(TileRedNetIO.class, s);
	}
	
	public void loadRecipes()
	{
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileRedNetIO(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(getMod().assets + "rednet_io");
		icon_input = ir.registerIcon(getMod().assets + "rednet_io_input");
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
		TileRedNetIO t = (TileRedNetIO) world.getTileEntity(x, y, z);
		if(t != null && !t.isInvalid()) t.onInputsChanged(side, inputValues);
	}
	
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{ onInputsChanged(world, x, y, z, side, new int[] {inputValue}); }
	
	@Optional.Method(modid = OtherMods.MFR)
	public powercrystals.minefactoryreloaded.api.rednet.connectivity.RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{ return powercrystals.minefactoryreloaded.api.rednet.connectivity.RedNetConnectionType.CableAll; }
	
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		TileRedNetIO t = (TileRedNetIO) world.getTileEntity(x, y, z);
		if(t != null && !t.isInvalid()) return t.getOutputValues(side);
		return new int[16];
	}
	
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{ return getOutputValues(world, x, y, z, side)[subnet]; }
}