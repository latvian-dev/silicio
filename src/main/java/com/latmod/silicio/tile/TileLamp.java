package com.latmod.silicio.tile;

import com.latmod.silicio.api.tile.ISilNetController;
import gnu.trove.impl.Constants;
import gnu.trove.map.TShortByteMap;
import gnu.trove.map.hash.TShortByteHashMap;
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

    private final TShortByteHashMap colorMap;
    private byte currentColor;

    public TileLamp()
    {
        colorMap = new TShortByteHashMap(1, Constants.DEFAULT_LOAD_FACTOR, (short) 0, NONE);
        currentColor = 0;
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);

        final int[] ai = new int[colorMap.size()];
        final int[] i = {-1};

        colorMap.forEachEntry((key, value) ->
        {
            ai[++i[0]] = key | (value << 16);
            return true;
        });

        nbt.setIntArray("Colors", ai);
        nbt.setByte("CurrentColor", currentColor);
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);

        colorMap.clear();

        for(int i : nbt.getIntArray("Colors"))
        {
            colorMap.put((short) (i & 0xFFFF), (byte) ((i >> 16) & 0xFF));
        }

        currentColor = nbt.getByte("CurrentColor");
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);

        final int[] ai = new int[colorMap.size()];
        final int[] i = {-1};

        colorMap.forEachEntry((key, value) ->
        {
            ai[++i[0]] = key | (value << 16);
            return true;
        });

        nbt.setIntArray("C", ai);
        nbt.setByte("CC", currentColor);
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);

        colorMap.clear();

        for(int i : nbt.getIntArray("C"))
        {
            colorMap.put((short) (i & 0xFFFF), (byte) ((i >> 16) & 0xFF));
        }

        currentColor = nbt.getByte("CC");
    }

    @Override
    protected boolean rerenderBlock()
    {
        return true;
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