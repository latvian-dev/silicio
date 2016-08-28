package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.lib.math.MathHelperLM;
import com.latmod.lib.util.LMTroveUtils;
import com.latmod.silicio.api.tile.ISilNetController;
import gnu.trove.impl.Constants;
import gnu.trove.map.TIntByteMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        nbt.setIntArray("Colors", LMTroveUtils.toIntList(colorMap).toArray());
        nbt.setInteger("CurrentColor", currentColor);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        colorMap = LMTroveUtils.fromArray(nbt.getIntArray("Colors"));
        currentColor = nbt.getInteger("CurrentColor");
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);
        nbt.setIntArray("Colors", LMTroveUtils.toIntList(colorMap).toArray());
        nbt.setInteger("CurrentColor", currentColor);
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
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
    public void onSignalsChanged(ISilNetController controller, TIntByteMap channels)
    {
        currentColor = Color.HSBtoRGB(MathHelperLM.RAND.nextFloat(), 1F, 1F);
        markDirty();

        channels.forEachEntry((channel, on) ->
        {
            int newColor = colorMap.get(channel);

            if(newColor != NONE)
            {
                currentColor = newColor;
                markDirty();
                return false;
            }

            return true;
        });
    }

    public int getCurrentColor()
    {
        return currentColor;
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }

    @Override
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