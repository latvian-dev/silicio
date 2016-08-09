package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.ItemBlockLM;
import com.feed_the_beast.ftbl.api.item.IMaterial;
import com.feed_the_beast.ftbl.util.MetaLookup;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileESU;
import com.latmod.silicio.tile.TileReactorCore;
import com.latmod.silicio.tile.TileSilNetController;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSilMachines extends BlockSil
{
    public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);

    public enum EnumVariant implements IMaterial
    {
        CONTROLLER(0, MapColor.BLUE, BlockRenderLayer.SOLID, TileSilNetController.class),
        MODULE_COPIER(1, MapColor.GRAY, BlockRenderLayer.SOLID, null),
        ESU(2, MapColor.LIGHT_BLUE, BlockRenderLayer.CUTOUT, TileESU.class),
        COMPUTER_IO(3, MapColor.YELLOW, BlockRenderLayer.SOLID, null),
        REACTOR_CORE(4, MapColor.GRAY, BlockRenderLayer.CUTOUT, TileReactorCore.class);

        public static final MetaLookup<EnumVariant> META_MAP = new MetaLookup<>(EnumVariant.values(), EnumVariant.CONTROLLER);
        public final MapColor mapColor;
        public final BlockRenderLayer layer;
        public final Class<? extends TileEntity> tileClass;
        private final String name;
        private final int meta;

        EnumVariant(int id, MapColor c, BlockRenderLayer l, Class<? extends TileEntity> t)
        {
            name = name().toLowerCase();
            meta = id;
            mapColor = c;
            layer = l;
            tileClass = t;
        }

        @Override
        public Item getItem()
        {
            return Item.getItemFromBlock(SilBlocks.MACHINES);
        }

        @Override
        public void setItem(Item item)
        {
        }

        @Override
        public int getMetadata()
        {
            return meta;
        }

        @Override
        public boolean isAdded()
        {
            return true;
        }

        @Nonnull
        @Override
        public String getName()
        {
            return name;
        }
    }

    public class ItemBlockMachines extends ItemBlockLM
    {
        public ItemBlockMachines(Block b)
        {
            super(b);
        }

        @Override
        @Nullable
        public String getNameFromVariant(int meta)
        {
            return BlockSilBlocks.EnumVariant.META_MAP.get(meta).getName();
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
            case CONTROLLER:
                return new TileSilNetController();
            case ESU:
                return new TileESU();
            case REACTOR_CORE:
                return new TileReactorCore();
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
        return getDefaultState().withProperty(VARIANT, EnumVariant.META_MAP.get(meta));
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