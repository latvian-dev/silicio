package com.latmod.silicio.api.pipes;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public interface IPipeConnection
{
    boolean canPipeConnect(@Nullable TransportedItem item);
}