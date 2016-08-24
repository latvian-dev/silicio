package com.latmod.silicio.item;

import com.latmod.lib.util.LMStringUtils;
import com.latmod.silicio.api.ISilNetController;
import com.latmod.silicio.api.ISilNetTile;
import com.latmod.silicio.api.SilCapabilities;
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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ItemIDCard extends ItemSil
{
    public ItemIDCard()
    {
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("ControllerID");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("ControllerID"))
        {
            tooltip.add(stack.getTagCompound().getString("ControllerID"));
        }
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tile = worldIn.getTileEntity(pos);

            if(tile != null && tile.hasCapability(SilCapabilities.SILNET_TILE, facing))
            {
                ISilNetTile silNetTile = tile.getCapability(SilCapabilities.SILNET_TILE, facing);

                if(silNetTile instanceof ISilNetController)
                {
                    if(!stack.hasTagCompound())
                    {
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setString("ControllerID", LMStringUtils.fromUUID(silNetTile.getControllerID()));
                }
                else if(stack.hasTagCompound() && stack.getTagCompound().hasKey("ControllerID"))
                {
                    UUID id = LMStringUtils.fromString(stack.getTagCompound().getString("ControllerID"));
                    silNetTile.setControllerID(id, playerIn);
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }
}
