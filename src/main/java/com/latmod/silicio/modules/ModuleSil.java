package com.latmod.silicio.modules;

import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.ModuleBase;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LatvianModder on 25.09.2016.
 */
public class ModuleSil extends ModuleBase
{
    public ModuleSil(String s)
    {
        super(new ResourceLocation(Silicio.MOD_ID, s));
    }
}
