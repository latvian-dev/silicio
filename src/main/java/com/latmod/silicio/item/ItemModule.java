package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.IRecipes;
import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.lib.LangKey;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api_impl.SilCaps;
import com.latmod.silicio.api_impl.module.ModuleContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemModule extends ItemSil
{
    private static final LangKey DESC = new LangKey("silicio.item.module_desc");

    public static void loadRecipes(IRecipes recipes)
    {
        Silicio.PROXY.moduleMap.forEach((key, value) -> value.addRecipes(Silicio.PROXY.moduleStackMap.get(key), recipes));
    }

    private ModelResourceLocation defaultModel;

    public ItemModule()
    {
        setMaxStackSize(1);
        setMaxDamage(0);
    }

    public void registerModels()
    {
        Silicio.PROXY.moduleMap.forEach((key, value) -> ModelBakery.registerItemVariants(this, value.getModelLocation()));

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
        subItems.addAll(Silicio.PROXY.moduleStackMap.values());
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
