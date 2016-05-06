package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import ftb.lib.MathHelperMC;
import ftb.lib.api.item.ODItems;
import ftb.lib.api.notification.Notification;
import latmod.silicio.api.tile.cb.CBHelper;
import latmod.silicio.api.tile.cb.ICBController;
import latmod.silicio.item.SilItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class BlockConnector extends BlockSil
{
	public static final AxisAlignedBB[] BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(2D / 16D, 0D, 2D / 16D, 14D / 16D, 2D / 16D, 14D / 16D), false);
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);
	
	public BlockConnector()
	{
		super(Material.ROCK);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " C ", "WSW", 'W', SilItems.WIRE, 'C', SilItems.CIRCUIT_WIFI, 'S', ODItems.STONE);
	}
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(FACING, EnumFacing.NORTH); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{ return BlockRenderLayer.SOLID; }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{ return false; }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta % 6]); }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return state.getValue(FACING).ordinal(); }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, FACING); }
	
	@Override
	public int damageDropped(IBlockState state)
	{ return 0; }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{ return getDefaultState().withProperty(FACING, facing.getOpposite()); }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{ return BOXES[state.getValue(FACING).ordinal()]; }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{ return false; }
	
	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase el, ItemStack is)
	{
		super.onBlockPlacedBy(w, pos, state, el, is);
		
		if(!w.isRemote)
		{
			ICBController link = CBHelper.linkWithClosestController(w, pos);
			
			if(link != null && el instanceof EntityPlayerMP)
			{
				Notification n = new Notification("silicio:linked_with_cb", new TextComponentString("Linked with controller"), 3000);
				n.desc = new TextComponentString(link.getTile().getPos().toString());
				FTBLib.notifyPlayer((EntityPlayerMP) el, n);
			}
		}
	}
}