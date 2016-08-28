package com.latmod.silicio.api.module;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public interface IModulePropertyKey extends IStringSerializable
{
    IModuleProperty getDefValue();

    ITextComponent getDisplayName();
}