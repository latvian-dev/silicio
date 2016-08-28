package com.latmod.silicio.api.module;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public interface IModuleProperty extends INBTSerializable<NBTBase>
{
    String getString();

    boolean getBoolean();

    int getInt();

    IModuleProperty copy();
}