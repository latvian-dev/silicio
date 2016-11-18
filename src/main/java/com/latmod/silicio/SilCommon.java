package com.latmod.silicio;

import com.latmod.silicio.api.ISilicioRegistry;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.api_impl.module.ModuleContainer;
import com.latmod.silicio.item.SilItems;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SilCommon implements ISilicioRegistry // SilClient
{
    public final Map<String, IModule> moduleMap = new HashMap<>();
    public final Map<String, ItemStack> moduleStackMap = new HashMap<>();

    public void preInit()
    {
        //FIXME: Load modules
    }

    @Override
    public void addModule(IModule module)
    {
        String key = module.getID().toString();
        moduleMap.put(key, module);
        ItemStack itemStack = new ItemStack(SilItems.MODULE);
        IModuleContainer container = itemStack.getCapability(SilCaps.MODULE_CONTAINER, null);
        ((ModuleContainer) container).setModule(module);
        moduleStackMap.put(key, itemStack);
    }
}