package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.item.LMInvUtils;
import com.latmod.silicio.api.SilicioAPI;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.module.impl.ModuleContainer;
import com.latmod.silicio.tile.TileSocketBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockSocketBlock extends BlockSil
{
    private static final PropertyBool MODULE_D = PropertyBool.create("down");
    private static final PropertyBool MODULE_U = PropertyBool.create("up");
    private static final PropertyBool MODULE_N = PropertyBool.create("north");
    private static final PropertyBool MODULE_S = PropertyBool.create("south");
    private static final PropertyBool MODULE_W = PropertyBool.create("west");
    private static final PropertyBool MODULE_E = PropertyBool.create("east");

    public BlockSocketBlock()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(MODULE_D, false).withProperty(MODULE_U, false).withProperty(MODULE_N, false).withProperty(MODULE_S, false).withProperty(MODULE_W, false).withProperty(MODULE_E, false));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World w, IBlockState state)
    {
        return new TileSocketBlock();
    }

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

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, MODULE_D, MODULE_U, MODULE_N, MODULE_S, MODULE_W, MODULE_E);
    }

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos)
    {
        boolean modD = false, modU = false, modN = false, modS = false, modW = false, modE = false;

        TileEntity te = w.getTileEntity(pos);

        if(te instanceof TileSocketBlock)
        {
            TileSocketBlock tile = (TileSocketBlock) te;
            modD = tile.modules.containsKey(EnumFacing.DOWN);
            modU = tile.modules.containsKey(EnumFacing.UP);
            modN = tile.modules.containsKey(EnumFacing.NORTH);
            modS = tile.modules.containsKey(EnumFacing.SOUTH);
            modW = tile.modules.containsKey(EnumFacing.WEST);
            modE = tile.modules.containsKey(EnumFacing.EAST);
        }

        return state.withProperty(MODULE_D, modD).withProperty(MODULE_U, modU).withProperty(MODULE_N, modN).withProperty(MODULE_S, modS).withProperty(MODULE_W, modW).withProperty(MODULE_E, modE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if(te instanceof TileSocketBlock)
        {
            TileSocketBlock socketBlock = (TileSocketBlock) te;

            if(socketBlock.modules.containsKey(side))
            {
                if(!worldIn.isRemote)
                {
                    if(heldItem == null && playerIn.isSneaking())
                    {
                        IModuleContainer c = socketBlock.modules.get(side);

                        if(c.getModule() != null)
                        {
                            c.getModule().onRemoved(c, (EntityPlayerMP) playerIn);
                        }

                        if(c.getItem() != null)
                        {
                            LMInvUtils.giveItem(playerIn, c.getItem());
                        }

                        socketBlock.modules.remove(side);
                        socketBlock.markDirty();
                    }
                }

                return true;
            }
            else if(heldItem != null && heldItem.hasCapability(SilicioAPI.MODULE_PROVIDER, null))
            {
                if(!worldIn.isRemote)
                {
                    IModule module = heldItem.getCapability(SilicioAPI.MODULE_PROVIDER, null).getModule();

                    ModuleContainer c = new ModuleContainer(socketBlock);
                    c.setFacing(side);
                    c.setItem(LMInvUtils.singleCopy(heldItem));
                    c.setModule(module);

                    socketBlock.modules.put(c.getFacing(), c);
                    heldItem.stackSize--;
                    c.getModule().onAdded(c, (EntityPlayerMP) playerIn);
                    socketBlock.markDirty();
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileSocketBlock)
            {
                for(IModuleContainer c : ((TileSocketBlock) te).modules.values())
                {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), c.getItem());
                }
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
