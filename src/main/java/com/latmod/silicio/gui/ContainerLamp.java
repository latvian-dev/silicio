package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.RegistryObject;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.feed_the_beast.ftbl.lib.gui.ContainerLM;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileLamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.09.2016.
 */
public class ContainerLamp extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "lamp");

    @RegistryObject
    public static final IGuiHandler HANDLER = new IGuiHandler()
    {
        @Override
        public ResourceLocation getID()
        {
            return ID;
        }

        @Override
        @Nullable
        public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileLamp) ? new ContainerLamp(player, (TileLamp) te) : null;
        }

        @Override
        @Nullable
        public Object getGui(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileLamp) ? new GuiLamp(new ContainerLamp(player, (TileLamp) te)).getWrapper() : null;
        }
    };

    public TileLamp tile;

    public ContainerLamp(EntityPlayer ep, TileLamp t)
    {
        super(ep);
        tile = t;
        addPlayerSlots(8, 76, false);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return null;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }
}