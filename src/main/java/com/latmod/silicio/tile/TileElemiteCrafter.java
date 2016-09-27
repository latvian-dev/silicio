package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.lib.tile.TileInvLM;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class TileElemiteCrafter extends TileInvLM
{
    public TileElemiteCrafter()
    {
        super(9);
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }
}
