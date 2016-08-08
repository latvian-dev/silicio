package com.latmod.silicio.api;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public interface IModuleProvider
{
    @Nullable
    IModule getModule();
}