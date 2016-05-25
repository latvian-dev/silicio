package latmod.silicio.tile;

import latmod.silicio.api.SignalChannel;
import latmod.silicio.api.tile.ISilNetController;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileLamp extends TileSilNet
{
    public boolean enabled;

    @Override
    public void writeTileClientData(NBTTagCompound tag)
    {
        if(enabled)
        {
            tag.setBoolean("E", true);
        }
    }

    @Override
    public void readTileClientData(NBTTagCompound tag)
    {
        enabled = tag.hasKey("E");
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.RERENDER;
    }

    @Override
    public void onUpdate()
    {
        if(worldObj.getTotalWorldTime() % 20L == 0L)
        {
            enabled = !enabled;
            markDirty();
        }
    }

    @Override
    public void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on)
    {
    }
}