package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.ItemBlockLM;
import com.feed_the_beast.ftbl.api.item.IMaterial;
import com.feed_the_beast.ftbl.util.MetaLookup;
import com.latmod.silicio.Silicio;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by LatvianModder on 05.02.2016.
 */
public class BlockSilBlocks extends BlockSil
{
    public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);

    public enum EnumVariant implements IMaterial
    {
        SILICON_BLOCK(0, MapColor.GRAY, BlockRenderLayer.TRANSLUCENT, Material.ROCK),
        SILICON_GLASS(1, MapColor.SILVER, BlockRenderLayer.TRANSLUCENT, Material.GLASS),
        DENSE_SILICON(2, MapColor.BLACK, BlockRenderLayer.SOLID, Material.ROCK),
        SILICON_FRAME(3, MapColor.SILVER, BlockRenderLayer.CUTOUT, Material.ROCK),
        ELEMITE(4, MapColor.BLUE, BlockRenderLayer.SOLID, Material.IRON),
        HEX(5, MapColor.BLACK, BlockRenderLayer.SOLID, Material.ROCK),
        HEX_COLOR(6, MapColor.BLACK, BlockRenderLayer.SOLID, Material.ROCK);

        public static final MetaLookup<EnumVariant> META_MAP = new MetaLookup<>(EnumVariant.values(), EnumVariant.SILICON_BLOCK);
        public final MapColor mapColor;
        public final BlockRenderLayer layer;
        public final Material material;
        private final String name;
        private final int meta;

        EnumVariant(int id, MapColor c, BlockRenderLayer l, Material m)
        {
            name = name().toLowerCase();
            meta = id;
            mapColor = c;
            layer = l;
            material = m;
        }

        @Override
        public Item getItem()
        {
            return Item.getItemFromBlock(SilBlocks.BLOCKS);
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

    public class ItemBlockBlocks extends ItemBlockLM
    {
        public ItemBlockBlocks(Block b)
        {
            super(b);
        }

        @Override
        @Nullable
        public String getNameFromVariant(int meta)
        {
            return EnumVariant.META_MAP.get(meta).getName();
        }
    }

    public BlockSilBlocks()
    {
        super(Material.ROCK);
        setCreativeTab(Silicio.tab);
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockBlocks(this);
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

    @Nonnull
    @Override
    @Deprecated
    public Material getMaterial(IBlockState state)
    {
        return state.getValue(VARIANT).material;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Deprecated
    public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos, EnumFacing side)
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