package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.BlockVariantLookup;
import com.feed_the_beast.ftbl.api.block.BlockWithVariants;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.LMRecipes;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.lib.math.BlockStateSerializer;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.client.RenderTurret;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileConnector;
import com.latmod.silicio.tile.TileLamp;
import com.latmod.silicio.tile.TileSocketBlock;
import com.latmod.silicio.tile.TileTurret;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class SilBlocks
{
    public static final BlockWithVariants<EnumSilBlocks> BLOCKS = Silicio.register("blocks", new BlockWithVariants<EnumSilBlocks>(Material.ROCK, Silicio.tab)
    {
        @Nonnull
        @Override
        public BlockVariantLookup<EnumSilBlocks> createMetaLookup()
        {
            return new BlockVariantLookup<>("variant", EnumSilBlocks.class, EnumSilBlocks.SILICON_BLOCK);
        }
    });

    public static final BlockWithVariants<EnumSilMachines> MACHINES = Silicio.register("machines", new BlockWithVariants<EnumSilMachines>(Material.IRON, Silicio.tab)
    {
        @Nonnull
        @Override
        public BlockVariantLookup<EnumSilMachines> createMetaLookup()
        {
            return new BlockVariantLookup<EnumSilMachines>("variant", EnumSilMachines.class, EnumSilMachines.CONTROLLER);
        }
    });
    public static final BlockSocketBlock SOCKET_BLOCK = Silicio.register("socket_block", new BlockSocketBlock());
    public static final BlockConnector CONNECTOR = Silicio.register("connector", new BlockConnector());
    public static final BlockBlueGoo BLUE_GOO_BLOCK = Silicio.register("blue_goo", new BlockBlueGoo());
    public static final BlockTurret TURRET = Silicio.register("turret", new BlockTurret());
    public static final BlockLamp LAMP = Silicio.register("lamp", new BlockLamp());

    public static void init()
    {
        OreDictionary.registerOre(ODItems.GLASS, EnumSilBlocks.SILICON_GLASS.getStack(1));

        BLOCKS.registerTileEntities();
        MACHINES.registerTileEntities();

        FTBLib.addTile(TileSocketBlock.class, SOCKET_BLOCK.getRegistryName());
        FTBLib.addTile(TileConnector.class, CONNECTOR.getRegistryName());
        FTBLib.addTile(TileTurret.class, TURRET.getRegistryName());
        FTBLib.addTile(TileLamp.class, LAMP.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        BLOCKS.registerModels();
        MACHINES.registerModels();

        SOCKET_BLOCK.registerDefaultModel();
        CONNECTOR.registerDefaultModel();
        BLUE_GOO_BLOCK.registerDefaultModel();
        TURRET.registerDefaultModel();

        Item item = Item.getItemFromBlock(LAMP);

        for(BlockLamp.EnumLampColor color : BlockLamp.EnumLampColor.VALUES)
        {
            ModelLoader.setCustomModelResourceLocation(item, color.ordinal(), new ModelResourceLocation(LAMP.getRegistryName(), BlockStateSerializer.getString(LAMP.getBlockState().getBaseState().withProperty(BlockLamp.ON, true).withProperty(BlockLamp.COLOR, color))));
        }

        ClientRegistry.bindTileEntitySpecialRenderer(TileTurret.class, new RenderTurret());
    }

    public static void loadRecipes()
    {
        LMRecipes.INSTANCE.addRecipe(EnumSilBlocks.ELEMITE.getStack(1), "III", "III", "III", 'I', SilItems.ORE_ELEMITE_INGOT);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.ELEMITE_INGOT.getStack(9), EnumSilBlocks.ELEMITE);
        LMRecipes.INSTANCE.addSmelting(EnumSilBlocks.ELEMITE.getStack(1), new ItemStack(SilBlocks.BLUE_GOO_BLOCK));
        LMRecipes.INSTANCE.addRecipe(EnumSilBlocks.DENSE_SILICON.getStack(1), "III", "III", "III", 'I', EnumSilBlocks.SILICON_BLOCK);
        LMRecipes.INSTANCE.addShapelessRecipe(EnumSilBlocks.SILICON_BLOCK.getStack(9), EnumSilBlocks.DENSE_SILICON);
        LMRecipes.INSTANCE.addRecipe(EnumSilBlocks.SILICON_FRAME.getStack(1), "ISI", "S S", "ISI", 'S', EnumSilBlocks.DENSE_SILICON, 'I', ODItems.IRON);
        LMRecipes.INSTANCE.addRecipe(EnumSilBlocks.SILICON_BLOCK.getStack(1), "SS", "SS", 'S', ODItems.SILICON);
        //LMRecipes.INSTANCE.addShapelessRecipe(SilItems.SILICON.getStack(4), EnumVariant.SILICON_BLOCK.getStack(1));
        LMRecipes.INSTANCE.addSmelting(EnumSilBlocks.SILICON_GLASS.getStack(1), EnumSilBlocks.SILICON_BLOCK.getStack(1));

        LMRecipes.INSTANCE.addRecipe(EnumSilMachines.CONTROLLER.getStack(1), " E ", "DFD", " P ", 'N', SilItems.ORE_ELEMITE_NUGGET, 'E', SilItems.CIRCUIT_WIFI, 'P', SilItems.PROCESSOR, 'F', EnumSilBlocks.SILICON_FRAME, 'D', ODItems.DIAMOND);
        LMRecipes.INSTANCE.addRecipe(EnumSilMachines.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', EnumSilBlocks.SILICON_FRAME, 'A', SilItems.ANTIMATTER, 'N', Items.NETHER_STAR, 'G', ODItems.GLOWSTONE);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(SOCKET_BLOCK), " P ", "PFP", " P ", 'P', SilItems.PROCESSOR, 'F', EnumSilBlocks.SILICON_FRAME);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(CONNECTOR), " C ", "WSW", 'W', SilItems.WIRE, 'C', SilItems.CIRCUIT_WIFI, 'S', ODItems.STONE);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(BLUE_GOO_BLOCK), "GGG", "GGG", "GGG", 'G', SilItems.BLUE_GOO);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.BLUE_GOO.getStack(9), BLUE_GOO_BLOCK);

        for(BlockLamp.EnumLampColor color : BlockLamp.EnumLampColor.VALUES)
        {
            LMRecipes.INSTANCE.addRecipe(new ItemStack(LAMP, 1, color.ordinal()), "SBS", "RDR", "SBS", 'D', color.dyeName, 'S', ODItems.STONE, 'R', ODItems.REDSTONE, 'B', SilItems.ELEMITE_DUST.getStack(1));
        }
    }
}