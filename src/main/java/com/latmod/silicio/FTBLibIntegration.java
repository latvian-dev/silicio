package com.latmod.silicio;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.FTBLibPlugin;
import com.feed_the_beast.ftbl.api.IFTBLibPlugin;
import com.feed_the_beast.ftbl.api.IFTBLibRegistry;
import com.feed_the_beast.ftbl.api.IRecipes;
import com.feed_the_beast.ftbl.api.client.IFTBLibClientRegistry;
import com.feed_the_beast.ftbl.lib.item.ODItems;
import com.latmod.silicio.block.EnumSilBlocks;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.gui.ContainerElemiteCrafter;
import com.latmod.silicio.gui.ContainerLamp;
import com.latmod.silicio.gui.ContainerModuleIO;
import com.latmod.silicio.gui.GuiElemiteCrafter;
import com.latmod.silicio.gui.GuiLamp;
import com.latmod.silicio.gui.GuiModuleIO;
import com.latmod.silicio.item.ItemModule;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileElemiteCrafter;
import com.latmod.silicio.tile.TileLamp;
import com.latmod.silicio.tile.TileModuleIO;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import static com.latmod.silicio.item.EnumMat.*;

/**
 * Created by LatvianModder on 20.09.2016.
 */
public enum FTBLibIntegration implements IFTBLibPlugin
{
    @FTBLibPlugin
    INSTANCE;

    public static FTBLibAPI API;

    @Override
    public void init(FTBLibAPI api)
    {
        API = api;
    }

    @Override
    public void registerCommon(IFTBLibRegistry reg)
    {
        reg.addGuiContainer(ContainerModuleIO.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileModuleIO) ? new ContainerModuleIO(player, (TileModuleIO) te) : null;
        });

        reg.addGuiContainer(ContainerElemiteCrafter.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileElemiteCrafter) ? new ContainerElemiteCrafter(player, (TileElemiteCrafter) te) : null;
        });

        reg.addGuiContainer(ContainerLamp.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileLamp) ? new ContainerLamp(player, (TileLamp) te) : null;
        });
    }

    @Override
    public void registerClient(IFTBLibClientRegistry reg)
    {
        reg.addGui(ContainerModuleIO.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileModuleIO) ? new GuiModuleIO(new ContainerModuleIO(player, (TileModuleIO) te)).getWrapper() : null;
        });

        reg.addGui(ContainerElemiteCrafter.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileElemiteCrafter) ? new GuiElemiteCrafter(new ContainerElemiteCrafter(player, (TileElemiteCrafter) te)).getWrapper() : null;
        });

        reg.addGui(ContainerLamp.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileLamp) ? new GuiLamp(new ContainerLamp(player, (TileLamp) te)).getWrapper() : null;
        });
    }

    @Override
    public void registerRecipes(IRecipes recipes)
    {
        // Blocks //

        recipes.addRecipe(EnumSilBlocks.ELEMITE_BLOCK.getStack(1), "III", "III", "III", 'I', SilItems.ORE_ELEMITE_INGOT);
        recipes.addShapelessRecipe(ELEMITE_INGOT.getStack(9), EnumSilBlocks.ELEMITE_BLOCK);
        recipes.addSmelting(EnumSilBlocks.ELEMITE_BLOCK.getStack(1), new ItemStack(SilBlocks.BLUE_GOO_BLOCK), 0F);
        recipes.addRecipe(EnumSilBlocks.DENSE_SILICON.getStack(1), "III", "III", "III", 'I', EnumSilBlocks.SILICON_BLOCK);
        recipes.addShapelessRecipe(EnumSilBlocks.SILICON_BLOCK.getStack(9), EnumSilBlocks.DENSE_SILICON);
        recipes.addRecipe(EnumSilBlocks.SILICON_FRAME.getStack(1), "ISI", "S S", "ISI", 'S', ODItems.SILICON, 'I', Blocks.IRON_BARS);
        recipes.addRecipe(EnumSilBlocks.SILICON_BLOCK.getStack(1), "SS", "SS", 'S', ODItems.SILICON);
        //recipes.addShapelessRecipe(SilItems.SILICON.getStack(4), EnumVariant.SILICON_BLOCK.getStack(1));
        recipes.addSmelting(EnumSilBlocks.SILICON_GLASS.getStack(1), EnumSilBlocks.SILICON_BLOCK.getStack(1), 0F);
        recipes.addRecipe(EnumSilBlocks.OPAL_BLOCK.getStack(1), "II", "II", 'I', OPAL);
        recipes.addShapelessRecipe(OPAL.getStack(4), EnumSilBlocks.OPAL_BLOCK);
        recipes.addRecipe(EnumSilBlocks.OPAL_GLASS.getStack(4), " G ", "GIG", " G ", 'I', OPAL, 'G', ODItems.GLASS_ANY);

        recipes.addRecipe(EnumSilBlocks.CONTROLLER.getStack(1), "PEP", "DFD", "PRP", 'N', SilItems.ORE_ELEMITE_NUGGET, 'E', CIRCUIT_WIFI, 'P', PROCESSOR, 'F', EnumSilBlocks.SILICON_FRAME, 'D', ODItems.DIAMOND, 'R', CIRCUIT);
        recipes.addRecipe(EnumSilBlocks.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', EnumSilBlocks.SILICON_FRAME, 'A', OPAL, 'N', Items.NETHER_STAR, 'G', ODItems.GLOWSTONE);
        recipes.addRecipe(EnumSilBlocks.ELEMITE_CRAFTER.getStack(1), " E ", "EFE", " C ", 'F', EnumSilBlocks.SILICON_FRAME, 'E', SilItems.ORE_ELEMITE_INGOT, 'C', "workbench");

        recipes.addRecipe(new ItemStack(SilBlocks.SOCKET_BLOCK), " P ", "PFP", " P ", 'P', PROCESSOR, 'F', EnumSilBlocks.SILICON_FRAME);
        recipes.addRecipe(new ItemStack(SilBlocks.CONNECTOR), " C ", "WSW", 'W', WIRE, 'C', CIRCUIT_WIFI, 'S', Blocks.STONE_PRESSURE_PLATE);
        recipes.addRecipe(new ItemStack(SilBlocks.BLUE_GOO_BLOCK), "GGG", "GGG", "GGG", 'G', BLUE_GOO);
        recipes.addShapelessRecipe(BLUE_GOO.getStack(9), SilBlocks.BLUE_GOO_BLOCK);
        recipes.addRecipe(EnumSilBlocks.LAMP.getStack(1), " L ", "LFL", " L ", 'F', EnumSilBlocks.SILICON_FRAME, 'L', LED_RGB);

        // Items //

        recipes.addShapelessRecipe(BLUE_GOO.getStack(1), ODItems.SLIMEBALL, ODItems.LAPIS, ODItems.IRON);
        recipes.addRecipe(ELEMITE_PLATE.getStack(1), "EEE", "ESE", "EEE", 'E', SilItems.ORE_ELEMITE_INGOT, 'S', ANTIMATTER);
        recipes.addRecipe(OPAL.getStack(4), "WSW", "SLS", "WSW", 'S', ODItems.SILICON, 'W', Items.WATER_BUCKET, 'L', Items.LAVA_BUCKET);

        recipes.addRecipe(ELEMITE_INGOT.getStack(1), "EEE", "EEE", "EEE", 'E', SilItems.ORE_ELEMITE_NUGGET);
        recipes.addShapelessRecipe(ELEMITE_NUGGET.getStack(9), SilItems.ORE_ELEMITE_INGOT);

        recipes.addSmelting(ELEMITE_INGOT.getStack(1), ELEMITE_DUST.getStack(1), 0F);
        recipes.addSmelting(ELEMITE_INGOT.getStack(1), BLUE_GOO.getStack(1), 0F);

        recipes.addRecipe(WIRE.getStack(8), "WWW", "NNN", "WWW", 'N', SilItems.ORE_ELEMITE_NUGGET, 'W', ODItems.STRING);
        recipes.addRecipe(RESISTOR.getStack(4), "WCW", 'C', Items.BRICK, 'W', WIRE);
        recipes.addRecipe(CAPACITOR.getStack(4), "WCW", 'C', Items.CLAY_BALL, 'W', WIRE);
        recipes.addRecipe(DIODE.getStack(4), "WCW", 'C', ODItems.SILICON, 'W', WIRE);
        recipes.addRecipe(TRANSISTOR.getStack(3), "DDD", "WWW", 'D', DIODE, 'W', WIRE);
        recipes.addRecipe(CHIP.getStack(1), "TT", "TT", "TT", 'T', TRANSISTOR);
        recipes.addRecipe(PROCESSOR.getStack(1), "CCC", "CSC", "CCC", 'C', CHIP, 'S', ODItems.SILICON);
        recipes.addShapelessRecipe(CIRCUIT.getStack(1), PROCESSOR, WIRE, RESISTOR, CAPACITOR, TRANSISTOR, PROCESSOR, WIRE, WIRE, SilItems.ORE_ELEMITE_NUGGET);
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

        recipes.addRecipe(new ItemStack(SilItems.ID_CARD), " P ", "PWP", " P ", 'P', ODItems.PAPER, 'W', CIRCUIT_WIFI);
        recipes.addRecipe(new ItemStack(SilItems.MULTITOOL), "CII", " AI", "  I", 'C', LASER_LENS, 'A', ANTIMATTER, 'I', ELEMITE_PLATE);

        ItemModule.loadRecipes(recipes);
    }
}