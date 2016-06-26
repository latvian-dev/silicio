package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.IBlockLM;
import com.feed_the_beast.ftbl.api.block.ItemBlockLM;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.util.BlockStateSerializer;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileCCBridge;
import com.latmod.silicio.tile.TileESU;
import com.latmod.silicio.tile.TileEUBridge;
import com.latmod.silicio.tile.TileReactorCore;
import com.latmod.silicio.tile.TileSilNetController;
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

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSilMachines extends BlockSil
{
    public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);

    public enum EnumVariant implements IStringSerializable
    {
        REACTOR_CORE(0, MapColor.GRAY, BlockRenderLayer.CUTOUT, TileReactorCore.class),
        ESU(1, MapColor.LIGHT_BLUE, BlockRenderLayer.CUTOUT, TileESU.class),
        ENERGY_BRIDGE_RF(2, MapColor.ADOBE, BlockRenderLayer.SOLID, TileCCBridge.class),
        ENERGY_BRIDGE_EU(3, MapColor.LIGHT_BLUE, BlockRenderLayer.SOLID, TileCCBridge.class),
        CONTROLLER(4, MapColor.BLUE, BlockRenderLayer.SOLID, TileSilNetController.class),
        MODULE_COPIER(5, MapColor.GRAY, BlockRenderLayer.SOLID, null),
        REDNET_IO(6, MapColor.RED, BlockRenderLayer.SOLID, null),
        COMPUTER_IO(7, MapColor.YELLOW, BlockRenderLayer.SOLID, null);

        private static final EnumVariant[] map = values();
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

        public static EnumVariant getVariantFromMeta(int meta)
        {
            if(meta >= 0 && meta < map.length)
            {
                return map[meta];
            }

            return EnumVariant.REACTOR_CORE;
        }

        // Static //

        @Nonnull
        @Override
        public String getName()
        {
            return name;
        }

        public ItemStack getStack(int q)
        {
            return new ItemStack(SilBlocks.MACHINES, q, meta);
        }
    }

    public class ItemBlockMachines extends ItemBlockLM
    {
        public ItemBlockMachines(IBlockLM b)
        {
            super(b);
        }

        @Nonnull
        @Override
        public String getUnlocalizedName(ItemStack stack)
        {
            return getMod().getBlockName(EnumVariant.getVariantFromMeta(stack.getMetadata()).getName());
        }
    }

    public BlockSilMachines()
    {
        super(Material.IRON);
        setCreativeTab(Silicio.tab);
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockMachines(this);
    }

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
        getMod().recipes.addRecipe(EnumVariant.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1), 'A', SilItems.ANTIMATTER, 'N', Items.NETHER_STAR, 'G', ODItems.GLOWSTONE);
        getMod().recipes.addRecipe(EnumVariant.CONTROLLER.getStack(1), " E ", "DFD", " P ", 'N', SilItems.ORE_ELEMITE_NUGGET, 'E', SilItems.CIRCUIT_WIFI, 'P', SilItems.PROCESSOR, 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1), 'D', ODItems.DIAMOND);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        Item item = getItem();

        for(EnumVariant e : EnumVariant.values())
        {
            ModelLoader.setCustomModelResourceLocation(item, e.meta, new ModelResourceLocation(getRegistryName(), BlockStateSerializer.getString(blockState.getBaseState().withProperty(VARIANT, e))));
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World w, @Nonnull IBlockState state)
    {
        switch(state.getValue(VARIANT))
        {
            case REACTOR_CORE:
                return new TileReactorCore();
            case ESU:
                return new TileESU();
            case ENERGY_BRIDGE_RF:
                return new TileCCBridge();
            case ENERGY_BRIDGE_EU:
                return new TileEUBridge();
            case CONTROLLER:
                return new TileSilNetController();
            default:
                return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumVariant e : EnumVariant.values())
        {
            list.add(new ItemStack(itemIn, 1, e.meta));
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).meta;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(VARIANT, EnumVariant.getVariantFromMeta(meta));
    }

    @Nonnull
    @Override
    @Deprecated
    public MapColor getMapColor(IBlockState state)
    {
        return state.getValue(VARIANT).mapColor;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).meta;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, @Nonnull BlockRenderLayer layer)
    {
        return layer == state.getValue(VARIANT).layer;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return state.getValue(VARIANT).layer == BlockRenderLayer.SOLID;
    }
}