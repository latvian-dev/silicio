package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.ItemMaterialsLM;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.modules.ModuleChatOutput;
import com.latmod.silicio.modules.ModuleSequencer;
import com.latmod.silicio.modules.ModuleTimer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.latmod.silicio.item.EnumMat.*;

public class SilItems
{
    public static final ItemMaterialsLM MAT = Silicio.register("mat", new ItemMaterialsLM());
    public static final ItemIDCard ID_CARD = Silicio.register("id_card", new ItemIDCard());

    public static final ItemMultiTool MULTITOOL = Silicio.register("multitool", new ItemMultiTool());
    //public static final ItemXSuitBelt XSUIT_BELT = Silicio.register("xsuit_belt", new ItemXSuitBelt());
    //public static final ItemXSuitVisor XSUIT_VISOR = Silicio.register("xsuit_visor", new ItemXSuitVisor());

    public static final String ORE_ELEMITE_INGOT = "ingotElemite";
    public static final String ORE_ELEMITE_NUGGET = "nuggetElemite";
    public static final String ORE_ELEMITE_DUST = "dustElemite";

    public static class Modules
    {
        private static final List<ItemModule> MODULE_LIST = new ArrayList<>();

        //public static final ItemModule COMMAND_BLOCK;
        //public static final ItemModule LIGHT_SENSOR;
        //public static final ItemModule SIGN_OUT;
        public static final ItemModule CHAT_OUT = register("chat_out", new ModuleChatOutput());
        public static final ItemModule TIMER = register("timer", new ModuleTimer());
        public static final ItemModule SEQUENCER = register("sequencer", new ModuleSequencer(4));
        //public static final ItemModule CRAFTING;

        //public static final ItemModule RS_IN;
        //public static final ItemModule RS_OUT;
        //public static final ItemModule ENERGY_IN;
        //public static final ItemModule ENERGY_OUT;

        //public static final ItemModule ITEM_STORAGE;
        //public static final ItemModule ITEM_IN;
        //public static final ItemModule ITEM_OUT;

        //public static final ItemModule FLUID_STORAGE;
        //public static final ItemModule FLUID_IN;
        //public static final ItemModule FLUID_OUT;

        //public static final ItemModule GATE_NOT;
        //public static final ItemModule GATE_AND;
        //public static final ItemModule GATE_OR;
        //public static final ItemModule GATE_XOR;

        private static ItemModule register(String s, IModule m)
        {
            ItemModule im = new ItemModule(m);
            MODULE_LIST.add(im);
            return Silicio.register("module_" + s, im);
        }

        public static void init()
        {
        }

        public static class Recipes implements IRecipeHandler
        {
            @Override
            public boolean isActive()
            {
                return true;
            }

            @Override
            public void loadRecipes(IRecipes recipes)
            {
                for(ItemModule m : Modules.MODULE_LIST)
                {
                    m.getModule().addRecipes(new ItemStack(m), recipes);
                }
            }
        }
    }

    public static void init()
    {
        MAT.setCreativeTab(Silicio.INST.tab);
        MAT.setFolder("materials");
        MAT.addAll(Arrays.asList(EnumMat.values()));
        MAT.setDefaultMaterial(EnumMat.BLUE_GOO);

        OreDictionary.registerOre(ORE_ELEMITE_DUST, EnumMat.ELEMITE_DUST.getStack(1));
        OreDictionary.registerOre(ORE_ELEMITE_INGOT, EnumMat.ELEMITE_INGOT.getStack(1));
        OreDictionary.registerOre(ORE_ELEMITE_NUGGET, EnumMat.ELEMITE_NUGGET.getStack(1));

        Modules.init();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        MAT.loadModels();
        ID_CARD.registerDefaultModel();
        MULTITOOL.registerDefaultModel();
        //XSUIT_BELT.registerDefaultModel();
        //XSUIT_VISOR.registerDefaultModel();

        for(ItemModule m : Modules.MODULE_LIST)
        {
            ResourceLocation rl = new ResourceLocation(m.getRegistryName().getResourceDomain(), "modules/" + m.getRegistryName().getResourcePath().substring(7));
            ModelLoader.setCustomModelResourceLocation(m, 0, new ModelResourceLocation(rl, "inventory"));
        }
    }

    public static class Recipes implements IRecipeHandler
    {
        @Override
        public boolean isActive()
        {
            return true;
        }

        @Override
        public void loadRecipes(IRecipes recipes)
        {
            recipes.addShapelessRecipe(BLUE_GOO.getStack(1), ODItems.SLIMEBALL, ODItems.LAPIS, ODItems.IRON);
            recipes.addRecipe(XSUIT_PLATE.getStack(1), "EEE", "ESE", "EEE", 'E', ORE_ELEMITE_INGOT, 'S', ANTIMATTER);

            recipes.addRecipe(ELEMITE_INGOT.getStack(1), "EEE", "EEE", "EEE", 'E', ORE_ELEMITE_NUGGET);
            recipes.addShapelessRecipe(ELEMITE_NUGGET.getStack(9), ORE_ELEMITE_INGOT);

            recipes.addSmelting(ELEMITE_INGOT.getStack(1), ELEMITE_DUST.getStack(1), 0F);
            recipes.addSmelting(ELEMITE_INGOT.getStack(1), BLUE_GOO.getStack(1), 0F);

            recipes.addRecipe(WIRE.getStack(8), "WWW", "NNN", "WWW", 'N', ORE_ELEMITE_NUGGET, 'W', ODItems.STRING);
            recipes.addRecipe(RESISTOR.getStack(4), "WCW", 'C', Items.BRICK, 'W', WIRE);
            recipes.addRecipe(CAPACITOR.getStack(4), "WCW", 'C', Items.CLAY_BALL, 'W', WIRE);
            recipes.addRecipe(DIODE.getStack(4), "WCW", 'C', ODItems.SILICON, 'W', WIRE);
            recipes.addRecipe(TRANSISTOR.getStack(3), "DDD", "WWW", 'D', DIODE, 'W', WIRE);
            recipes.addRecipe(CHIP.getStack(1), "TT", "TT", "TT", 'T', TRANSISTOR);
            recipes.addRecipe(PROCESSOR.getStack(1), "CCC", "CSC", "CCC", 'C', CHIP, 'S', ODItems.SILICON);
            recipes.addShapelessRecipe(CIRCUIT.getStack(1), PROCESSOR, WIRE, RESISTOR, CAPACITOR, TRANSISTOR, PROCESSOR, WIRE, WIRE, ORE_ELEMITE_NUGGET);
            recipes.addRecipe(CIRCUIT_WIFI.getStack(8), "CCC", "CEC", "CCC", 'C', CIRCUIT, 'E', Items.ENDER_PEARL);
            recipes.addShapelessRecipe(LED_RED.getStack(3), DIODE, EnumDyeColor.RED, ODItems.GLOWSTONE);
            recipes.addShapelessRecipe(LED_GREEN.getStack(3), DIODE, EnumDyeColor.GREEN, ODItems.GLOWSTONE);
            recipes.addShapelessRecipe(LED_BLUE.getStack(3), DIODE, EnumDyeColor.BLUE, ODItems.GLOWSTONE);
            recipes.addShapelessRecipe(LED_RGB.getStack(3), LED_RED, LED_GREEN, LED_BLUE);
            recipes.addRecipe(LED_MATRIX.getStack(1), "LLL", "LLL", "LLL", 'L', LED_RGB);

            recipes.addRecipe(MODULE_EMPTY.getStack(1), "III", "ICI", "III", 'I', ODItems.IRON, 'C', PROCESSOR);
            recipes.addShapelessRecipe(MODULE_INPUT.getStack(1), MODULE_EMPTY, EnumDyeColor.LIGHT_BLUE);
            recipes.addShapelessRecipe(MODULE_OUTPUT.getStack(1), MODULE_EMPTY, EnumDyeColor.ORANGE);
            recipes.addShapelessRecipe(MODULE_LOGIC.getStack(1), MODULE_EMPTY, ODItems.REDSTONE);

            recipes.addRecipe(new ItemStack(ID_CARD), " P ", "PWP", " P ", 'P', ODItems.PAPER, 'W', CIRCUIT_WIFI);
            recipes.addRecipe(new ItemStack(MULTITOOL), "CII", " AI", "  I", 'C', LASER_LENS, 'A', ANTIMATTER, 'I', XSUIT_PLATE);
        }
    }
}