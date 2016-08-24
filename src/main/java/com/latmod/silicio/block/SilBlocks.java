package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.BlockVariantLookup;
import com.feed_the_beast.ftbl.api.block.BlockWithVariants;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.client.RenderLamp;
import com.latmod.silicio.client.RenderTurret;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileConnector;
import com.latmod.silicio.tile.TileLamp;
import com.latmod.silicio.tile.TileSocketBlock;
import com.latmod.silicio.tile.TileTurret;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

import static com.latmod.silicio.item.EnumMat.ANTIMATTER;
import static com.latmod.silicio.item.EnumMat.BLUE_GOO;
import static com.latmod.silicio.item.EnumMat.CIRCUIT_WIFI;
import static com.latmod.silicio.item.EnumMat.ELEMITE_INGOT;
import static com.latmod.silicio.item.EnumMat.LED_RGB;
import static com.latmod.silicio.item.EnumMat.OPAL;
import static com.latmod.silicio.item.EnumMat.PROCESSOR;
import static com.latmod.silicio.item.EnumMat.WIRE;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class SilBlocks
{
    public static final BlockWithVariants<EnumSilBlocks> BLOCKS = Silicio.register("blocks", new BlockWithVariants<EnumSilBlocks>(Material.ROCK, Silicio.INST.tab)
    {
        @Nonnull
        @Override
        public BlockVariantLookup<EnumSilBlocks> createMetaLookup()
        {
            return new BlockVariantLookup<>("variant", EnumSilBlocks.class, EnumSilBlocks.SILICON_BLOCK);
        }
    });

    public static final BlockSocketBlock SOCKET_BLOCK = Silicio.register("socket_block", new BlockSocketBlock());
    public static final BlockConnector CONNECTOR = Silicio.register("connector", new BlockConnector());
    public static final BlockBlueGoo BLUE_GOO_BLOCK = Silicio.register("blue_goo", new BlockBlueGoo());
    public static final BlockTurret TURRET = Silicio.register("turret", new BlockTurret());

    public static void init()
    {
        OreDictionary.registerOre(ODItems.GLASS, EnumSilBlocks.SILICON_GLASS.getStack(1));

        BLOCKS.registerTileEntities();

        FTBLib.addTile(TileSocketBlock.class, SOCKET_BLOCK.getRegistryName());
        FTBLib.addTile(TileConnector.class, CONNECTOR.getRegistryName());
        FTBLib.addTile(TileTurret.class, TURRET.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        BLOCKS.registerModels();
        SOCKET_BLOCK.registerDefaultModel();
        CONNECTOR.registerDefaultModel();
        BLUE_GOO_BLOCK.registerDefaultModel();
        TURRET.registerDefaultModel();

        ClientRegistry.bindTileEntitySpecialRenderer(TileTurret.class, new RenderTurret());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new RenderLamp());
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
            recipes.addRecipe(EnumSilBlocks.OPAL_GLASS.getStack(4), " G ", "GIG", " G ", 'I', OPAL, 'G', ODItems.GLASS_ANY);

            recipes.addRecipe(EnumSilBlocks.CONTROLLER.getStack(1), " E ", "DFD", " P ", 'N', SilItems.ORE_ELEMITE_NUGGET, 'E', CIRCUIT_WIFI, 'P', PROCESSOR, 'F', EnumSilBlocks.SILICON_FRAME, 'D', ODItems.DIAMOND);
            recipes.addRecipe(EnumSilBlocks.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', EnumSilBlocks.SILICON_FRAME, 'A', ANTIMATTER, 'N', Items.NETHER_STAR, 'G', ODItems.GLOWSTONE);
            recipes.addRecipe(EnumSilBlocks.ELEMITE_CRAFTER.getStack(1), "EEE", "SCS", "SSS", 'S', EnumSilBlocks.SILICON_BLOCK, 'E', SilItems.ORE_ELEMITE_INGOT, 'C', "workbench");

            recipes.addRecipe(new ItemStack(SOCKET_BLOCK), " P ", "PFP", " P ", 'P', PROCESSOR, 'F', EnumSilBlocks.SILICON_FRAME);
            recipes.addRecipe(new ItemStack(CONNECTOR), " C ", "WSW", 'W', WIRE, 'C', CIRCUIT_WIFI, 'S', ODItems.STONE);
            recipes.addRecipe(new ItemStack(BLUE_GOO_BLOCK), "GGG", "GGG", "GGG", 'G', BLUE_GOO);
            recipes.addShapelessRecipe(BLUE_GOO.getStack(9), BLUE_GOO_BLOCK);
            recipes.addRecipe(EnumSilBlocks.LAMP.getStack(1), " L ", "LFL", " L ", 'F', EnumSilBlocks.SILICON_FRAME, 'L', LED_RGB);
        }
    }
}