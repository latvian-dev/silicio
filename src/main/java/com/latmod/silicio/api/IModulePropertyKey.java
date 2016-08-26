package com.latmod.silicio.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public interface IModulePropertyKey<N extends IModuleProperty> extends IStringSerializable
{
    N getDefValue();

    ITextComponent getDisplayName();
}