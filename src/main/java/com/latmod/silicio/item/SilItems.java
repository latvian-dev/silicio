package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.ItemMaterialsLM;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.api.recipes.RecipeHandler;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModule;
import com.latmod.silicio.modules.ModuleChatOutput;
import com.latmod.silicio.modules.ModuleSequencer;
import com.latmod.silicio.modules.ModuleTimer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.latmod.silicio.item.EnumMat.*;

public class SilItems
{
    public static final ItemMaterialsLM MAT = new ItemMaterialsLM();
    public static final ItemIDCard ID_CARD = new ItemIDCard();
    public static final ItemMultiTool MULTITOOL = new ItemMultiTool();

    //public static final ItemXSuitBelt XSUIT_BELT = Silicio.register("xsuit_belt", new ItemXSuitBelt());
    //public static final ItemXSuitVisor XSUIT_VISOR = Silicio.register("xsuit_visor", new ItemXSuitVisor());

    public static final String ORE_ELEMITE_INGOT = "ingotElemite";
    public static final String ORE_ELEMITE_NUGGET = "nuggetElemite";
    public static final String ORE_ELEMITE_DUST = "dustElemite";

    public static class Modules
    {
        public static final Map<String, ItemModule> MODULE_LIST = new HashMap<>();

        //public static final ItemModule COMMAND_BLOCK;
        //public static final ItemModule LIGHT_SENSOR;
        //public static final ItemModule SIGN_OUT;
        public static final ItemModule CHAT_OUT = create("chat_out", new ModuleChatOutput());
        public static final ItemModule TIMER = create("timer", new ModuleTimer());
        public static final ItemModule SEQUENCER = create("sequencer", new ModuleSequencer(4));
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

        private static ItemModule create(String s, IModule m)
        {
            ItemModule im = new ItemModule(m);
            MODULE_LIST.put(s, im);
            return im;
        }

        @RecipeHandler
        public static final IRecipeHandler RECIPES = new IRecipeHandler()
        {
            @Override
            public ResourceLocation getID()
            {
                return new ResourceLocation(Silicio.MOD_ID, "modules");
            }

            @Override
            public boolean isActive()
            {
                return true;
            }

            @Override
            public void loadRecipes(IRecipes recipes)
            {
                Modules.MODULE_LIST.forEach((key, value) -> value.getModule().addRecipes(new ItemStack(value), recipes));
            }
        };
    }

    public static void init()
    {
        Silicio.register("mat", MAT);
        Silicio.register("id_card", ID_CARD);
        Silicio.register("multitool", MULTITOOL);

        MAT.setCreativeTab(Silicio.INST.tab);
        MAT.setFolder("materials");
        MAT.addAll(Arrays.asList(EnumMat.values()));
        MAT.setDefaultMaterial(EnumMat.BLUE_GOO);

        OreDictionary.registerOre(ORE_ELEMITE_DUST, EnumMat.ELEMITE_DUST.getStack(1));
        OreDictionary.registerOre(ORE_ELEMITE_INGOT, EnumMat.ELEMITE_INGOT.getStack(1));
        OreDictionary.registerOre(ORE_ELEMITE_NUGGET, EnumMat.ELEMITE_NUGGET.getStack(1));

        Modules.MODULE_LIST.forEach((key, value) -> Silicio.register("module_" + key, value));
    }

    @RecipeHandler
    public static final IRecipeHandler RECIPES = new IRecipeHandler()
    {
        @Override
        public ResourceLocation getID()
        {
            return new ResourceLocation(Silicio.MOD_ID, "items");
        }

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
            recipes.addRecipe(OPAL.getStack(4), "WSW", "SLS", "WSW", 'S', ODItems.SILICON, 'W', Items.WATER_BUCKET, 'L', Items.LAVA_BUCKET);

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
    };
}