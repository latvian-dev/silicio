package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.tile.ISilNetController;
import gnu.trove.impl.Constants;
import gnu.trove.map.TShortByteMap;
import gnu.trove.map.hash.TIntByteHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileLamp extends TileSilNet
{
    public static final AxisAlignedBB LAMP_BOX = new AxisAlignedBB(1D / 16D, 1D / 16D, 1D / 16D, 15D / 16D, 15D / 16D, 15D / 16D);
    private static final byte NONE = 0;

    private final TIntByteHashMap colorMap;
    private byte currentColor;

    public TileLamp()
    {
        colorMap = new TIntByteHashMap(1, Constants.DEFAULT_LOAD_FACTOR, 0, NONE);
        currentColor = 0;
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);

        int[] a = new int[colorMap.size() * 2];

        final int[] i = {-1};

        colorMap.forEachEntry((key, value) ->
        {
            a[++i[0]] = key;
            a[++i[0]] = value;
            return true;
        });

        nbt.setIntArray("Colors", a);
        nbt.setByte("CurrentColor", currentColor);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);

        colorMap.clear();

        int[] a = nbt.getIntArray("Colors");
        if(a.length > 0)
        {
            for(int i = 0; i < a.length; i += 2)
            {
                colorMap.put(a[i], (byte) a[i + 1]);
            }
        }

        currentColor = nbt.getByte("CurrentColor");
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);

        int[] a = new int[colorMap.size() * 2];

        final int[] i = {-1};

        colorMap.forEachEntry((key, value) ->
        {
            a[++i[0]] = key;
            a[++i[0]] = value;
            return true;
        });

        nbt.setIntArray("C", a);

        nbt.setByte("CC", currentColor);
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);

        colorMap.clear();

        int[] a = nbt.getIntArray("C");
        if(a.length > 0)
        {
            for(int i = 0; i < a.length; i += 2)
            {
                colorMap.put(a[i], (byte) a[i + 1]);
            }
        }

        currentColor = nbt.getByte("CC");
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.SYNC;
    }

    @Override
    public void onSignalsChanged(ISilNetController controller, TShortByteMap channels)
    {
        channels.forEachEntry((channel, on) ->
        {
            byte newColor = colorMap.get(channel);

            if(newColor != NONE)
            {
                currentColor = newColor;
                markDirty();
                return false;
            }

            return true;
        });
    }

    public byte getCurrentColor()
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
        return LAMP_BOX.addCoord(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536D;
    }
}