package com.latmod.silicio.api.tile;

import com.latmod.silicio.api.SignalChannel;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ISilNetController extends ISilNetTile
{
    boolean getSignalState(SignalChannel channel);

    void addToNetwork(BlockPos pos);

    Collection<BlockPos> getNetwork();
}