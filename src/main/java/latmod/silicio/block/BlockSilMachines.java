package latmod.silicio.block;

import ftb.lib.BlockStateSerializer;
import ftb.lib.FTBLib;
import ftb.lib.api.block.IBlockLM;
import ftb.lib.api.block.ItemBlockLM;
import ftb.lib.api.item.ODItems;
import latmod.silicio.SilItems;
import latmod.silicio.Silicio;
import latmod.silicio.item.ItemSilMaterials;
import latmod.silicio.tile.TileCBController;
import latmod.silicio.tile.TileESU;
import latmod.silicio.tile.TileEUBridge;
import latmod.silicio.tile.TileRFBridge;
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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSilMachines extends BlockSil
{
	public enum EnumVariant implements IStringSerializable
	{
		REACTOR_CORE(0, MapColor.grayColor, BlockRenderLayer.CUTOUT, TileReactorCore.class),
		ESU(1, MapColor.lightBlueColor, BlockRenderLayer.CUTOUT, TileESU.class),
		ENERGY_BRIDGE_RF(2, MapColor.adobeColor, BlockRenderLayer.SOLID, TileRFBridge.class),
		ENERGY_BRIDGE_EU(3, MapColor.lightBlueColor, BlockRenderLayer.SOLID, TileRFBridge.class),
		CONTROLLER(4, MapColor.blueColor, BlockRenderLayer.SOLID, TileCBController.class);
		
		public final String name;
		public final int meta;
		public final MapColor mapColor;
		public final BlockRenderLayer layer;
		public final Class<? extends TileEntity> tileClass;
		
		EnumVariant(int id, MapColor c, BlockRenderLayer l, Class<? extends TileEntity> t)
		{
			name = name().toLowerCase();
			meta = id;
			mapColor = c;
			layer = l;
			tileClass = t;
		}
		
		@Override
		public String getName()
		{ return name; }
		
		public ItemStack getStack(int q)
		{ return new ItemStack(SilItems.b_machines, q, meta); }
		
		// Static //
		
		private static final EnumVariant[] map = values();
		
		public static EnumVariant getVariantFromMeta(int meta)
		{
			if(meta >= 0 && meta < map.length)
			{
				return map[meta];
			}
			
			return EnumVariant.REACTOR_CORE;
		}
	}
	
	public class ItemBlockMachines extends ItemBlockLM
	{
		public ItemBlockMachines(IBlockLM b)
		{ super(b); }
		
		@Override
		public String getUnlocalizedName(ItemStack stack)
		{ return getMod().getBlockName(EnumVariant.getVariantFromMeta(stack.getMetadata()).getName()); }
	}
	
	public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);
	
	public BlockSilMachines()
	{
		super(Material.iron);
		setCreativeTab(Silicio.tab);
	}
	
	@Override
	public ItemBlock createItemBlock()
	{ return new ItemBlockMachines(this); }
	
	@Override
	public void loadTiles()
	{
		for(EnumVariant e : EnumVariant.values())
		{
			FTBLib.addTile(e.tileClass, new ResourceLocation(Silicio.mod.getID(), e.getName()));
		}
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(EnumVariant.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1), 'A', ItemSilMaterials.ANTIMATTER.getStack(1), 'N', Items.nether_star, 'G', ODItems.GLOWSTONE);
		
		getMod().recipes.addRecipe(EnumVariant.CONTROLLER.getStack(1), " E ", "DFD", " P ", 'N', ItemSilMaterials.ELEMITE_NUGGET, 'E', ItemSilMaterials.CIRCUIT_WIFI, 'P', ItemSilMaterials.PROCESSOR, 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1), 'D', ODItems.DIAMOND);
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
	public boolean hasTileEntity(IBlockState state)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World w, IBlockState state)
	{
		switch(state.getValue(VARIANT))
		{
			case REACTOR_CORE:
				return new TileReactorCore();
			case ESU:
				return new TileESU();
			case ENERGY_BRIDGE_RF:
				return new TileRFBridge();
			case ENERGY_BRIDGE_EU:
				return new TileEUBridge();
			case CONTROLLER:
				return new TileCBController();
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
}