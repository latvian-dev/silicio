package latmod.silicio.block;

import ftb.lib.mod.FTBLibMod;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSimpleBlocks extends BlockSil
{
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
	
	public BlockSimpleBlocks(String s)
	{
		super(s, Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumType.dense_silicon));
	}
	
	public TileEntity createNewTileEntity(World w, int m)
	{ return null; }
	
	public void onPostLoaded()
	{
		loadModels();
	}
	
	public void loadModels()
	{
		String modid = getMod().ID;
		Item item = getItem();
		
		for(EnumType e : EnumType.values())
		{
			FTBLibMod.proxy.addItemModel(modid, item, e.meta, e.getName());
		}
	}
	
	public int damageDropped(IBlockState state)
	{ return state.getValue(VARIANT).meta; }
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(EnumType e : EnumType.values())
		{
			list.add(new ItemStack(itemIn, 1, e.meta));
		}
	}
	
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta)); }
	
	public MapColor getMapColor(IBlockState state)
	{ return state.getValue(VARIANT).mapColor; }
	
	public int getMetaFromState(IBlockState state)
	{ return state.getValue(VARIANT).meta; }
	
	protected BlockState createBlockState()
	{ return new BlockState(this, VARIANT); }
	
	public static enum EnumType implements IStringSerializable
	{
		dense_silicon(0, "dense_silicon", MapColor.blackColor),
		elemite(1, "elemite", MapColor.blueColor);
		
		private static final EnumType[] META_LOOKUP = new EnumType[16];
		public final int meta;
		public final MapColor mapColor;
		
		EnumType(int id, String s, MapColor c)
		{
			meta = id;
			mapColor = c;
		}
		
		public static EnumType byMetadata(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		public String getName()
		{
			return name();
		}
		
		static
		{
			for(EnumType e : values())
			{
				META_LOOKUP[e.meta] = e;
			}
		}
	}
}