package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.lib.util.LMStringUtils;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISilNetTile;
import com.latmod.silicio.api_impl.SilCaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ItemIDCard extends ItemSil
{
    private static final String TAG_KEY = "ControllerID";

    public ItemIDCard()
    {
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_KEY);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_KEY))
        {
            tooltip.add(stack.getTagCompound().getString(TAG_KEY));
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tile = worldIn.getTileEntity(pos);

            if(tile != null && tile.hasCapability(SilCaps.SILNET_TILE, facing))
            {
                ISilNetTile silNetTile = tile.getCapability(SilCaps.SILNET_TILE, facing);

                if(silNetTile instanceof ISilNetController)
                {
                    if(!stack.hasTagCompound())
                    {
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setString(TAG_KEY, LMStringUtils.fromUUID(silNetTile.getControllerID()));
                }
                else if(stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_KEY))
                {
                    UUID id = LMStringUtils.fromString(stack.getTagCompound().getString(TAG_KEY));
                    silNetTile.setControllerID(id, playerIn);
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }
}
