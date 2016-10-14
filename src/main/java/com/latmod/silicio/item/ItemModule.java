package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.config.IConfigTree;
import com.feed_the_beast.ftbl.api.config.IConfigValue;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.lib.LangKey;
import com.feed_the_beast.ftbl.lib.config.ConfigTree;
import com.feed_the_beast.ftbl.lib.config.EmptyConfigTree;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISocketBlock;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.api_impl.SilicioAPI_Impl;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemModule extends ItemSil
{
    private static final LangKey DESC = new LangKey("silicio.item.module_desc");
    private static final Map<String, IModule> MODULE_MAP = new HashMap<>();
    private static final Map<String, ItemStack> MODULE_STACK_MAP = new HashMap<>();

    private static class ModuleContainer implements IModuleContainer, ICapabilityProvider, INBTSerializable<NBTTagCompound>
    {
        private long tick;
        private IModule module;
        private IConfigTree properties;

        @Nullable
        @Override
        public IModule getModule()
        {
            return module;
        }

        @Override
        public long getTick()
        {
            return tick;
        }

        @Override
        public IConfigValue getProperty(IConfigKey config)
        {
            IConfigValue base = properties == null ? null : properties.get(config);
            return base == null ? config.getDefValue() : base;
        }

        @Override
        public IConfigTree getProperties()
        {
            if(properties == null)
            {
                return EmptyConfigTree.INSTANCE;
            }

            return properties;
        }

        public void loadProperties()
        {
            Collection<IConfigKey> propertyKeys = module == null ? Collections.emptyList() : module.getProperties();

            if(propertyKeys.isEmpty())
            {
                properties = null;
            }
            else
            {
                properties = new ConfigTree();

                for(IConfigKey key : propertyKeys)
                {
                    properties.add(key, key.getDefValue().copy());
                }
            }
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
        {
            return capability == SilCaps.MODULE_CONTAINER;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == SilCaps.MODULE_CONTAINER)
            {
                return (T) this;
            }

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = new NBTTagCompound();

            if(module != null)
            {
                nbt.setString("ID", module.getID().toString());
            }

            nbt.setLong("Tick", tick);

            if(properties != null && !properties.isEmpty())
            {
                nbt.setTag("Config", properties.serializeNBT());
            }

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            module = MODULE_MAP.get(nbt.getString("ID"));
            tick = nbt.getLong("Tick");
            properties = null;

            loadProperties();

            if(properties != null)
            {
                properties.deserializeNBT(nbt.getCompoundTag("Config"));
            }
        }

        @Override
        public void tick(ISocketBlock socketBlock)
        {
            if(module != null)
            {
                module.onUpdate(socketBlock);
            }

            tick++;
        }
    }

    public void findModules()
    {
        SilicioAPI_Impl.INSTANCE.asmData.findRegistryObjects(IModule.class, false, (obj, field, id) ->
        {
            String key = obj.getID().toString();
            MODULE_MAP.put(key, obj);
            ItemStack itemStack = new ItemStack(this);
            IModuleContainer container = itemStack.getCapability(SilCaps.MODULE_CONTAINER, null);
            ((ModuleContainer) container).module = obj;
            MODULE_STACK_MAP.put(key, itemStack);
        });
    }

    public static void loadRecipes(IRecipes recipes)
    {
        MODULE_MAP.forEach((key, value) -> value.addRecipes(MODULE_STACK_MAP.get(key), recipes));
    }

    private ModelResourceLocation defaultModel;

    public ItemModule()
    {
        setMaxStackSize(1);
        setMaxDamage(0);
    }

    public void registerModels()
    {
        MODULE_MAP.forEach((key, value) -> ModelBakery.registerItemVariants(this, value.getModelLocation()));

        ModelLoader.setCustomMeshDefinition(this, stack ->
        {
            IModule module = stack.getCapability(SilCaps.MODULE_CONTAINER, null).getModule();

            if(module != null)
            {
                return module.getModelLocation();
            }

            if(defaultModel == null)
            {
                defaultModel = new ModelResourceLocation(new ResourceLocation(Silicio.MOD_ID, "materials/module_empty"), "inventory");
            }

            return defaultModel;
        });
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, final NBTTagCompound nbt)
    {
        return new ModuleContainer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        subItems.addAll(MODULE_STACK_MAP.values());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        IModule module = stack.getCapability(SilCaps.MODULE_CONTAINER, null).getModule();

        if(module != null)
        {
            return module.getUnlocalizedName();
        }

        return super.getUnlocalizedName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add(DESC.translate());

        if(advanced || GuiScreen.isShiftKeyDown())
        {
            IModuleContainer moduleContainer = stack.getCapability(SilCaps.MODULE_CONTAINER, null);
            IModule module = moduleContainer.getModule();

            if(module == null)
            {
                tooltip.add("ID: unknown");
                return;
            }

            tooltip.add("ID: " + module.getID());
            tooltip.add("Tick: " + moduleContainer.getTick());
            tooltip.add("Properties:");

            for(IConfigKey key : module.getProperties())
            {
                ITextComponent line = new TextComponentString("> ");
                line.appendSibling(key.getDisplayName());
                line.appendText(": ");
                line.appendText(moduleContainer.getProperty(key).getString());
                line.getStyle().setColor(TextFormatting.GRAY);
                tooltip.add(line.getFormattedText());
            }
        }
    }
}
