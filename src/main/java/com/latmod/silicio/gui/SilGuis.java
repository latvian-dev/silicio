package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.IFTBLibRegistries;
import com.feed_the_beast.ftbl.api.gui.GuiHelper;
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

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class SilGuis
{
    public static final ResourceLocation ELEMITE_CRAFTER = new ResourceLocation(Silicio.MOD_ID, "elemite_crafter");
    public static final ResourceLocation MODULE_IO = new ResourceLocation(Silicio.MOD_ID, "module_io");

    public static void init(IFTBLibRegistries reg)
    {
        reg.guis().register(ELEMITE_CRAFTER, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = GuiHelper.getTile(player, data);
                return (te instanceof TileElemiteCrafter) ? new ContainerElemiteCrafter(player, (TileElemiteCrafter) te) : null;
            }

            @Override
            @Nullable
            public GuiScreen getGui(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = GuiHelper.getTile(player, data);
                return (te instanceof TileElemiteCrafter) ? new GuiElemiteCrafter(new ContainerElemiteCrafter(player, (TileElemiteCrafter) te)).getWrapper() : null;
            }
        });

        reg.guis().register(MODULE_IO, new IGuiHandler()
        {
            @Override
            @Nullable
            public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = GuiHelper.getTile(player, data);
                return (te instanceof TileModuleIO) ? new ContainerModuleIO(player, (TileModuleIO) te) : null;
            }

            @Override
            @Nullable
            public GuiScreen getGui(EntityPlayer player, @Nullable NBTTagCompound data)
            {
                TileEntity te = GuiHelper.getTile(player, data);
                return (te instanceof TileModuleIO) ? new GuiModuleIO(new ContainerModuleIO(player, (TileModuleIO) te)).getWrapper() : null;
            }
        });
    }
}
