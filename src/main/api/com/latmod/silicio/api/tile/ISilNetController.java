package com.latmod.silicio.api.tile;

import net.minecraft.nbt.NBTTagCompound;
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

    boolean getSignal(short id);

    void provideSignal(short id);

    Collection<TileEntity> getNetwork();

    Map<UUID, ISilNetConnector> getConnectors();

    NBTTagCompound getVariables();
}