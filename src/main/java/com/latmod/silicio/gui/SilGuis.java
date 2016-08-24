package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileElemiteCrafter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class SilGuis
{
    public static final ResourceLocation ELEMITE_CRAFTER = new ResourceLocation(Silicio.MOD_ID, "elemite_crafter");

    public static void init()
    {
        FTBLibAPI.get().getRegistries().guis().register(ELEMITE_CRAFTER, new IGuiHandler()
        {
            @Override
            public Container getContainer(@Nonnull EntityPlayer ep, @Nullable NBTTagCompound data)
            {
                TileEntity te = ep.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
                return (te instanceof TileElemiteCrafter) ? new ContainerElemiteCrafter(ep, (TileElemiteCrafter) te) : null;
            }

            @Override
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(@Nonnull EntityPlayer ep, @Nullable NBTTagCompound data)
            {
                TileEntity te = ep.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
                return (te instanceof TileElemiteCrafter) ? new GuiElemiteCrafter(new ContainerElemiteCrafter(ep, (TileElemiteCrafter) te)).getWrapper() : null;
            }
        });
    }
}
