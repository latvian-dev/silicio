package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.api.block.IBlockLM;
import ftb.lib.api.block.ItemBlockLM;
import ftb.lib.api.item.ODItems;
import latmod.silicio.SilBlocks;
import latmod.silicio.Silicio;
import latmod.silicio.item.ItemSilMaterials;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSilBlocks extends BlockSil
{
	public enum EnumVariant implements IStringSerializable
	{
		SILICON_BLOCK(0, MapColor.GRAY, BlockRenderLayer.TRANSLUCENT, Material.ROCK),
		SILICON_GLASS(1, MapColor.SILVER, BlockRenderLayer.TRANSLUCENT, Material.GLASS),
		DENSE_SILICON(2, MapColor.BLACK, BlockRenderLayer.SOLID, Material.ROCK),
		SILICON_FRAME(3, MapColor.SILVER, BlockRenderLayer.CUTOUT, Material.ROCK),
		ELEMITE(4, MapColor.BLUE, BlockRenderLayer.SOLID, Material.IRON);
		
		public final String name;
		public final int meta;
		public final MapColor mapColor;
		public final BlockRenderLayer layer;
		public final Material material;
		
		EnumVariant(int id, MapColor c, BlockRenderLayer l, Material m)
		{
			name = name().toLowerCase();
			meta = id;
			mapColor = c;
			layer = l;
			material = m;
		}
		
		@Override
		public String getName()
		{ return name; }
		
		public ItemStack getStack(int q)
		{ return new ItemStack(SilBlocks.BLOCKS, q, meta); }
		
		// Static //
		
		private static final EnumVariant[] map = values();
		
		public static EnumVariant getVariantFromMeta(int meta)
		{
			if(meta >= 0 && meta < map.length)
			{
				return map[meta];
			}
			
			return EnumVariant.SILICON_BLOCK;
		}
	}
	
	public class ItemBlockBlocks extends ItemBlockLM
	{
		public ItemBlockBlocks(IBlockLM b)
		{ super(b); }
		
		@Override
		public String getUnlocalizedName(ItemStack stack)
		{ return getMod().getBlockName(EnumVariant.getVariantFromMeta(stack.getMetadata()).getName()); }
	}
	
	public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);
	
	public BlockSilBlocks()
	{
		super(Material.ROCK);
		setCreativeTab(Silicio.tab);
	}
	
	@Override
	public ItemBlock createItemBlock()
	{ return new ItemBlockBlocks(this); }
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(ODItems.GLASS, new ItemStack(this));
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(EnumVariant.ELEMITE.getStack(1), "III", "III", "III", 'I', ItemSilMaterials.ELEMITE_INGOT.getStack(1));
		getMod().recipes.addShapelessRecipe(ItemSilMaterials.ELEMITE_INGOT.getStack(9), EnumVariant.ELEMITE.getStack(1));
		getMod().recipes.addSmelting(EnumVariant.ELEMITE.getStack(1), new ItemStack(SilBlocks.BLUE_GOO));
		
		getMod().recipes.addRecipe(EnumVariant.DENSE_SILICON.getStack(1), "III", "III", "III", 'I', EnumVariant.SILICON_BLOCK.getStack(1));
		getMod().recipes.addShapelessRecipe(EnumVariant.SILICON_BLOCK.getStack(9), EnumVariant.DENSE_SILICON.getStack(1));
		
		getMod().recipes.addRecipe(EnumVariant.SILICON_FRAME.getStack(1), "ISI", "S S", "ISI", 'S', EnumVariant.DENSE_SILICON.getStack(1), 'I', ODItems.IRON);
		
		getMod().recipes.addRecipe(EnumVariant.SILICON_BLOCK.getStack(1), "SS", "SS", 'S', ItemSilMaterials.SILICON.getStack(1));
		getMod().recipes.addShapelessRecipe(ItemSilMaterials.SILICON.getStack(4), EnumVariant.SILICON_BLOCK.getStack(1));
		
		getMod().recipes.addSmelting(EnumVariant.SILICON_GLASS.getStack(1), EnumVariant.SILICON_BLOCK.getStack(1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void loadModels()
	{
		Item item = getItem();
		
		for(EnumVariant e : EnumVariant.values())
		{
			ModelLoader.setCustomModelResourceLocation(item, e.meta, new ModelResourceLocation(getRegistryName(), BlockStateSerializer.getString(VARIANT, e)));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(EnumVariant e : EnumVariant.values())
		{
			list.add(new ItemStack(itemIn, 1, e.meta));
		}
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{ return state.getValue(VARIANT).meta; }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState().withProperty(VARIANT, EnumVariant.getVariantFromMeta(meta)); }
	
	@Override
	public MapColor getMapColor(IBlockState state)
	{ return state.getValue(VARIANT).mapColor; }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return state.getValue(VARIANT).meta; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, VARIANT); }
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{ return layer == state.getValue(VARIANT).layer; }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{ return state.getValue(VARIANT).layer == BlockRenderLayer.SOLID; }
	
	@Override
	public Material getMaterial(IBlockState state)
	{ return state.getValue(VARIANT).material; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		if(state.getValue(VARIANT) == EnumVariant.SILICON_GLASS)
		{
			IBlockState state1 = worldIn.getBlockState(pos.offset(side));
			
			if(state1.getBlock() == this && state1.getValue(VARIANT) == EnumVariant.SILICON_GLASS)
			{
				return false;
			}
		}
		
		return super.shouldSideBeRendered(state, worldIn, pos, side);
	}
}