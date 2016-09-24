package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.gui.ContainerLM;
import com.feed_the_beast.ftbl.api.gui.GuiHandler;
import com.feed_the_beast.ftbl.api.gui.GuiHelper;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileModuleIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerModuleIO extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "module_io");

    @GuiHandler
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
            return (te instanceof TileModuleIO) ? new ContainerModuleIO(player, (TileModuleIO) te) : null;
        }

        @Override
        @Nullable
        public Object getGui(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileModuleIO) ? new GuiModuleIO(new ContainerModuleIO(player, (TileModuleIO) te)).getWrapper() : null;
        }
    };

    public TileModuleIO tile;

    public ContainerModuleIO(EntityPlayer ep, TileModuleIO t)
    {
        super(ep);
        tile = t;
        addSlotToContainer(new SlotItemHandler(t.itemHandler, 0, 13, 11));
        addSlotToContainer(new SlotItemHandler(t.itemHandler, 1, 13, 33));
        addPlayerSlots(8, 76, false);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile.itemHandler;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }
}