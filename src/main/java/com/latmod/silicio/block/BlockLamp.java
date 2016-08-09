package com.latmod.silicio.block;

import com.latmod.silicio.tile.TileLamp;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockLamp extends BlockSil
{
    public static final PropertyBool ON = PropertyBool.create("on");
    public static final PropertyEnum<EnumLampColor> COLOR = PropertyEnum.create("color", EnumLampColor.class);

    public enum EnumLampColor implements IStringSerializable
    {
        BLUE("dyeBlue", MapColor.BLUE),
        CYAN("dyeCyan", MapColor.CYAN),
        GREEN("dyeGreen", MapColor.GREEN),
        YELLOW("dyeYellow", MapColor.YELLOW),
        ORANGE("dyeOrange", MapColor.ADOBE),
        RED("dyeRed", MapColor.RED),
        PINK("dyePink", MapColor.PINK),
        PURPLE("dyePurple", MapColor.PURPLE);

        public static final EnumLampColor[] VALUES = values();

        public final String name;
        public final String dyeName;
        public final MapColor mapColor;

        EnumLampColor(String dye, MapColor m)
        {
            name = name().toLowerCase();
            dyeName = dye;
            mapColor = m;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }

    public BlockLamp()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(COLOR, EnumLampColor.BLUE).withProperty(ON, false));
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
        return new TileLamp();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state) % 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(int i = 0; i < 8; i++)
        {
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
    {
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR, ON);
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COLOR, EnumLampColor.VALUES[meta % 8]).withProperty(ON, meta >= 8);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR).ordinal() + (state.getValue(ON) ? 8 : 0);
    }

    @Override
    @Deprecated
    public MapColor getMapColor(IBlockState state)
    {
        return state.getValue(COLOR).mapColor;
    }
}