package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.lib.util.LMTroveUtils;
import com.latmod.silicio.api.ISilNetController;
import gnu.trove.impl.Constants;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileLamp extends TileSilNet
{
    public static final AxisAlignedBB LAMP_BOX = new AxisAlignedBB(1D / 16D, 1D / 16D, 1D / 16D, 15D / 16D, 15D / 16D, 15D / 16D);
    private static final int NONE = 0xFFFFFFFF;

    private TIntIntMap colorMap;
    private int currentColor;

    public TileLamp()
    {
        colorMap = new TIntIntHashMap(1, Constants.DEFAULT_LOAD_FACTOR, 0, NONE);
        currentColor = 0xFFFFFF;
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        nbt.setIntArray("Colors", LMTroveUtils.toIntList(colorMap).toArray());
        nbt.setInteger("CurrentColor", currentColor);
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        colorMap = LMTroveUtils.fromArray(nbt.getIntArray("Colors"));
        currentColor = nbt.getInteger("CurrentColor");
    }

    @Override
    public void writeTileClientData(@Nonnull NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);
        nbt.setIntArray("Colors", LMTroveUtils.toIntList(colorMap).toArray());
        nbt.setInteger("CurrentColor", currentColor);
    }

    @Override
    public void readTileClientData(@Nonnull NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);
        colorMap = LMTroveUtils.fromArray(nbt.getIntArray("Colors"));
        currentColor = nbt.getInteger("CurrentColor");
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.SYNC;
    }

    @Override
    public void onSignalChanged(@Nonnull ISilNetController c, int ch, boolean on)
    {
    }

    public int getCurrentColor()
    {
        float hue = (worldObj.getTotalWorldTime() + (pos.getX() + pos.getY() + pos.getZ()) * 10) * 0.01F;
        currentColor = Color.HSBtoRGB(hue, 1F, 1F);
        return currentColor;
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return LAMP_BOX.offset(pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536D;
    }
}