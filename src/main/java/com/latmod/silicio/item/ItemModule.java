package com.latmod.silicio.item;

import com.latmod.lib.LangKey;
import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleProvider;
import com.latmod.silicio.api.SilCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemModule extends ItemSil
{
    private static final LangKey DESC = new LangKey("silicio.item.module_desc");

    private class ModuleCapProvider implements IModuleProvider, ICapabilityProvider
    {
        @Override
        public IModule getModule()
        {
            return ItemModule.this.getModule();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == SilCapabilities.MODULE_PROVIDER;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == SilCapabilities.MODULE_PROVIDER)
            {
                return (T) this;
            }

            return null;
        }
    }

    private final IModule module;

    public ItemModule(IModule m)
    {
        module = m;
        setMaxStackSize(1);
        setMaxDamage(0);
    }

    public IModule getModule()
    {
        return module;
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, final NBTTagCompound nbt)
    {
        return new ModuleCapProvider();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add(DESC.translate());
    }
}
