package com.latmod.silicio.item;

import com.latmod.silicio.api.IModule;
import com.latmod.silicio.api.IModuleProvider;
import com.latmod.silicio.api.SilCapabilities;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
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
    private class ModuleCapProvider implements IModuleProvider, ICapabilityProvider
    {
        @Override
        public IModule getModule()
        {
            return module;
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

    public IModule module;

    public ItemModule(IModule m)
    {
        module = m;
        setMaxStackSize(1);
        setMaxDamage(0);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, final NBTTagCompound nbt)
    {
        return new ModuleCapProvider();
    }

    @Override
    public void loadRecipes()
    {
        module.addRecipes(new ItemStack(this));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        subItems.add(new ItemStack(itemIn));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
    {
    }
}
