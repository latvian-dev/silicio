package com.latmod.silicio.api;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ISilNetTile
{
    @Nullable
    UUID getControllerID();

    void setControllerID(@Nullable UUID id, @Nonnull EntityPlayer playerIn);

    void provideSignals(@Nonnull ISilNetController c);

    void onSignalChanged(@Nonnull ISilNetController c, int channel, boolean on);
}