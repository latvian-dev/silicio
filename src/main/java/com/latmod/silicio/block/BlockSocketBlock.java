package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.notification.Notification;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.SilNetHelper;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileModuleSocket;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockSocketBlock extends BlockSil
{
    public static final PropertyBool MODULE_D = PropertyBool.create("down");
    public static final PropertyBool MODULE_U = PropertyBool.create("up");
    public static final PropertyBool MODULE_N = PropertyBool.create("north");
    public static final PropertyBool MODULE_S = PropertyBool.create("south");
    public static final PropertyBool MODULE_W = PropertyBool.create("west");
    public static final PropertyBool MODULE_E = PropertyBool.create("east");
    public static final PropertyBool CENTER = PropertyBool.create("center");

    public BlockSocketBlock()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(MODULE_D, false).withProperty(MODULE_U, false).withProperty(MODULE_N, false).withProperty(MODULE_S, false).withProperty(MODULE_W, false).withProperty(MODULE_E, false).withProperty(CENTER, true));
    }

    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TileModuleSocket.class, getRegistryName());
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this), " P ", "PFP", " P ", 'P', SilItems.PROCESSOR, 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1));
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
        return new TileModuleSocket();
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, MODULE_D, MODULE_U, MODULE_N, MODULE_S, MODULE_W, MODULE_E, CENTER);
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess w, BlockPos pos)
    {
        boolean modD = false, modU = false, modN = false, modS = false, modW = false, modE = false;

        TileModuleSocket tile = (TileModuleSocket) w.getTileEntity(pos);

        if(tile != null)
        {
            modD = tile.modules.containsKey(EnumFacing.DOWN);
            modU = tile.modules.containsKey(EnumFacing.UP);
            modN = tile.modules.containsKey(EnumFacing.NORTH);
            modS = tile.modules.containsKey(EnumFacing.SOUTH);
            modW = tile.modules.containsKey(EnumFacing.WEST);
            modE = tile.modules.containsKey(EnumFacing.EAST);
        }

        return state.withProperty(MODULE_D, modD).withProperty(MODULE_U, modU).withProperty(MODULE_N, modN).withProperty(MODULE_S, modS).withProperty(MODULE_W, modW).withProperty(MODULE_E, modE).withProperty(CENTER, true);
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase el, ItemStack is)
    {
        super.onBlockPlacedBy(w, pos, state, el, is);

        if(!w.isRemote)
        {
            ISilNetController link = SilNetHelper.linkWithClosestController(w, pos);

            if(link != null && el instanceof EntityPlayerMP)
            {
                Notification n = new Notification("silicio:linked_with_cb");
                n.addText(new TextComponentString("Linked with controller"));
                n.addText(new TextComponentString(((TileEntity) link).getPos().toString()));
                n.sendTo((EntityPlayerMP) el);
            }
        }
    }
}
