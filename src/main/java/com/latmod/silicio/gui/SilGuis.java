package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileElemiteCrafter;
import com.latmod.silicio.tile.TileModuleIO;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class SilGuis
{
    public static final ResourceLocation ELEMITE_CRAFTER = new ResourceLocation(Silicio.MOD_ID, "elemite_crafter");
    public static final ResourceLocation MODULE_IO = new ResourceLocation(Silicio.MOD_ID, "module_io");

    @Nullable
    private static TileEntity getTile(EntityPlayer player, NBTTagCompound data)
    {
        return player.worldObj.getTileEntity(new BlockPos(data.getInteger("X"), data.getInteger("Y"), data.getInteger("Z")));
    }

    public static void init()
    {
        FTBLibAPI.get().getRegistries().guis().register(ELEMITE_CRAFTER, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, NBTTagCompound data)
            {
                TileEntity te = getTile(player, data);
                return (te instanceof TileElemiteCrafter) ? new ContainerElemiteCrafter(player, (TileElemiteCrafter) te) : null;
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(EntityPlayer player, NBTTagCompound data)
            {
                TileEntity te = getTile(player, data);
                return (te instanceof TileElemiteCrafter) ? new GuiElemiteCrafter(new ContainerElemiteCrafter(player, (TileElemiteCrafter) te)).getWrapper() : null;
            }
        });

        FTBLibAPI.get().getRegistries().guis().register(MODULE_IO, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, NBTTagCompound data)
            {
                TileEntity te = getTile(player, data);
                return (te instanceof TileModuleIO) ? new ContainerModuleIO(player, (TileModuleIO) te) : null;
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public GuiScreen getGui(EntityPlayer player, NBTTagCompound data)
            {
                TileEntity te = getTile(player, data);
                return (te instanceof TileModuleIO) ? new GuiModuleIO(new ContainerModuleIO(player, (TileModuleIO) te)).getWrapper() : null;
            }
        });
    }
}
