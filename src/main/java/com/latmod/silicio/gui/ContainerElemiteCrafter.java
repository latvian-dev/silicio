package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.gui.GuiHandler;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.feed_the_beast.ftbl.lib.gui.ContainerLM;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileElemiteCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class ContainerElemiteCrafter extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(Silicio.MOD_ID, "elemite_crafter");

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
            return (te instanceof TileElemiteCrafter) ? new ContainerElemiteCrafter(player, (TileElemiteCrafter) te) : null;
        }

        @Override
        @Nullable
        public Object getGui(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileElemiteCrafter) ? new GuiElemiteCrafter(new ContainerElemiteCrafter(player, (TileElemiteCrafter) te)).getWrapper() : null;
        }
    };

    public final TileElemiteCrafter tile;

    public ContainerElemiteCrafter(EntityPlayer ep, TileElemiteCrafter t)
    {
        super(ep);
        tile = t;

        addPlayerSlots(8, 84, false);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile.itemHandler;
    }
}
