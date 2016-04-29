package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import ftb.lib.api.item.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.Silicio;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileReactorCore;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSilBlocks extends BlockSil
{
	public enum EnumVariant implements IStringSerializable
	{
		SILICON_BLOCK(0, MapColor.grayColor, BlockRenderLayer.TRANSLUCENT),
		SILICON_GLASS(1, MapColor.silverColor, BlockRenderLayer.TRANSLUCENT),
		DENSE_SILICON(2, MapColor.blackColor, BlockRenderLayer.SOLID),
		SILICON_FRAME(3, MapColor.silverColor, BlockRenderLayer.CUTOUT),
		ELEMITE(4, MapColor.blueColor, BlockRenderLayer.SOLID),
		REACTOR_CORE(5, MapColor.grayColor, BlockRenderLayer.CUTOUT);
		
		public final String name;
		public final int meta;
		public final MapColor mapColor;
		public final BlockRenderLayer layer;
		
		EnumVariant(int id, MapColor c, BlockRenderLayer l)
		{
			name = name().toLowerCase();
			meta = id;
			mapColor = c;
			layer = l;
		}
		
		@Override
		public String getName()
		{ return name; }
		
		public ItemStack getStack(int q)
		{ return new ItemStack(SilItems.b_blocks, q, meta); }
		
		// Static //
		
		private static final Map<Integer, EnumVariant> map = new HashMap<>();
		
		static
		{
			for(EnumVariant v : values())
			{
				map.put(v.meta, v);
			}
		}
		
		public static EnumVariant getVariantFromMeta(int meta)
		{
			if(map.containsKey(meta))
			{
				return map.get(meta);
			}
			
			return EnumVariant.SILICON_BLOCK;
		}
	}
	
	public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);
	
	public BlockSilBlocks()
	{
		super(Material.rock);
		setCreativeTab(Silicio.tab);
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(TileReactorCore.class, new ResourceLocation("silicio", "reactor_core"));
	}
	
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
		getMod().recipes.addSmelting(EnumVariant.ELEMITE.getStack(1), new ItemStack(SilItems.b_blue_goo));
		
		getMod().recipes.addRecipe(EnumVariant.DENSE_SILICON.getStack(1), "III", "III", "III", 'I', EnumVariant.SILICON_BLOCK.getStack(1));
		getMod().recipes.addShapelessRecipe(EnumVariant.SILICON_BLOCK.getStack(9), EnumVariant.DENSE_SILICON.getStack(1));
		
		getMod().recipes.addRecipe(EnumVariant.SILICON_FRAME.getStack(1), "ISI", "S S", "ISI", 'S', EnumVariant.DENSE_SILICON.getStack(1), 'I', ODItems.IRON);
		getMod().recipes.addRecipe(EnumVariant.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', EnumVariant.SILICON_FRAME.getStack(1), 'A', ItemSilMaterials.ANTIMATTER.getStack(1), 'N', Items.nether_star, 'G', ODItems.GLOWSTONE);
		
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
	public String getUnlocalizedName(ItemStack stack)
	{ return getMod().getBlockName(EnumVariant.getVariantFromMeta(stack.getMetadata()).getName()); }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		switch(state.getValue(VARIANT))
		{
			case REACTOR_CORE:
				return true;
			default:
				return false;
		}
	}
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{
		switch(state.getValue(VARIANT))
		{
			case REACTOR_CORE:
				return new TileReactorCore();
			default:
				return null;
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		if(state.getValue(VARIANT) == EnumVariant.SILICON_GLASS)
		{
			return true;
			//return worldIn.getBlockState(pos).getBlock() != this && super.shouldSideBeRendered(state, worldIn, pos, side);
		}
		
		return super.shouldSideBeRendered(state, worldIn, pos, side);
	}
	
	@Override
	public int getLightOpacity(IBlockState state)
	{
		return super.getLightOpacity(state);
	}
}