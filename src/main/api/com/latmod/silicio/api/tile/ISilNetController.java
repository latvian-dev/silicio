package com.latmod.silicio.api.tile;

import net.minecraft.tileentity.TileEntity;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ISilNetController extends ISilNetTile
{
    void onSilNetUpdate();

    boolean getSignal(int id);

    void provideSignal(int id);

    Collection<TileEntity> getNetwork();

    Map<UUID, ISilNetConnector> getConnectors();
}