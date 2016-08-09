package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.LMRecipes;
import com.feed_the_beast.ftbl.util.BlockStateSerializer;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.client.RenderTurret;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileConnector;
import com.latmod.silicio.tile.TileLamp;
import com.latmod.silicio.tile.TileSocketBlock;
import com.latmod.silicio.tile.TileTurret;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class SilBlocks
{
    public static final BlockSilBlocks BLOCKS = Silicio.register("blocks", new BlockSilBlocks());
    public static final BlockSilMachines MACHINES = Silicio.register("machines", new BlockSilMachines());
    public static final BlockSocketBlock SOCKET_BLOCK = Silicio.register("socket_block", new BlockSocketBlock());
    public static final BlockConnector CONNECTOR = Silicio.register("connector", new BlockConnector());
    public static final BlockBlueGoo BLUE_GOO_BLOCK = Silicio.register("blue_goo", new BlockBlueGoo());
    public static final BlockTurret TURRET = Silicio.register("turret", new BlockTurret());
    public static final BlockLamp LAMP = Silicio.register("lamp", new BlockLamp());

    public static void init()
    {
        OreDictionary.registerOre(ODItems.GLASS, BlockSilBlocks.EnumVariant.SILICON_GLASS.getStack(1));

        for(BlockSilMachines.EnumVariant e : BlockSilMachines.EnumVariant.values())
        {
            FTBLib.addTile(e.tileClass, new ResourceLocation(MACHINES.getRegistryName().getResourceDomain(), e.getName()));
        }

        FTBLib.addTile(TileSocketBlock.class, SOCKET_BLOCK.getRegistryName());
        FTBLib.addTile(TileConnector.class, CONNECTOR.getRegistryName());
        FTBLib.addTile(TileTurret.class, TURRET.getRegistryName());
        FTBLib.addTile(TileLamp.class, LAMP.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        Item item = Item.getItemFromBlock(BLOCKS);

        for(BlockSilBlocks.EnumVariant e : BlockSilBlocks.EnumVariant.values())
        {
            ModelLoader.setCustomModelResourceLocation(item, e.getMetadata(), new ModelResourceLocation(BLOCKS.getRegistryName(), BlockStateSerializer.getString(BLOCKS.getBlockState().getBaseState().withProperty(BlockSilBlocks.VARIANT, e))));
        }

        item = Item.getItemFromBlock(MACHINES);

        for(BlockSilMachines.EnumVariant e : BlockSilMachines.EnumVariant.values())
        {
            ModelLoader.setCustomModelResourceLocation(item, e.getMetadata(), new ModelResourceLocation(MACHINES.getRegistryName(), BlockStateSerializer.getString(MACHINES.getBlockState().getBaseState().withProperty(BlockSilMachines.VARIANT, e))));
        }

        SOCKET_BLOCK.addDefaultModel();
        CONNECTOR.addDefaultModel();
        BLUE_GOO_BLOCK.addDefaultModel();
        TURRET.addDefaultModel();

        item = Item.getItemFromBlock(LAMP);

        for(BlockLamp.EnumLampColor color : BlockLamp.EnumLampColor.VALUES)
        {
            ModelLoader.setCustomModelResourceLocation(item, color.ordinal(), new ModelResourceLocation(LAMP.getRegistryName(), BlockStateSerializer.getString(LAMP.getBlockState().getBaseState().withProperty(BlockLamp.ON, true).withProperty(BlockLamp.COLOR, color))));
        }

        ClientRegistry.bindTileEntitySpecialRenderer(TileTurret.class, new RenderTurret());
    }

    public static void loadRecipes()
    {
        LMRecipes.INSTANCE.addRecipe(BlockSilBlocks.EnumVariant.ELEMITE.getStack(1), "III", "III", "III", 'I', SilItems.ORE_ELEMITE_INGOT);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.ELEMITE_INGOT.getStack(9), BlockSilBlocks.EnumVariant.ELEMITE);
        LMRecipes.INSTANCE.addSmelting(BlockSilBlocks.EnumVariant.ELEMITE.getStack(1), new ItemStack(SilBlocks.BLUE_GOO_BLOCK));
        LMRecipes.INSTANCE.addRecipe(BlockSilBlocks.EnumVariant.DENSE_SILICON.getStack(1), "III", "III", "III", 'I', BlockSilBlocks.EnumVariant.SILICON_BLOCK);
        LMRecipes.INSTANCE.addShapelessRecipe(BlockSilBlocks.EnumVariant.SILICON_BLOCK.getStack(9), BlockSilBlocks.EnumVariant.DENSE_SILICON);
        LMRecipes.INSTANCE.addRecipe(BlockSilBlocks.EnumVariant.SILICON_FRAME.getStack(1), "ISI", "S S", "ISI", 'S', BlockSilBlocks.EnumVariant.DENSE_SILICON, 'I', ODItems.IRON);
        LMRecipes.INSTANCE.addRecipe(BlockSilBlocks.EnumVariant.SILICON_BLOCK.getStack(1), "SS", "SS", 'S', ODItems.SILICON);
        //LMRecipes.INSTANCE.addShapelessRecipe(SilItems.SILICON.getStack(4), EnumVariant.SILICON_BLOCK.getStack(1));
        LMRecipes.INSTANCE.addSmelting(BlockSilBlocks.EnumVariant.SILICON_GLASS.getStack(1), BlockSilBlocks.EnumVariant.SILICON_BLOCK.getStack(1));

        LMRecipes.INSTANCE.addRecipe(BlockSilMachines.EnumVariant.CONTROLLER.getStack(1), " E ", "DFD", " P ", 'N', SilItems.ORE_ELEMITE_NUGGET, 'E', SilItems.CIRCUIT_WIFI, 'P', SilItems.PROCESSOR, 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME, 'D', ODItems.DIAMOND);
        LMRecipes.INSTANCE.addRecipe(BlockSilMachines.EnumVariant.REACTOR_CORE.getStack(1), " N ", "AFA", " G ", 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME, 'A', SilItems.ANTIMATTER, 'N', Items.NETHER_STAR, 'G', ODItems.GLOWSTONE);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(SOCKET_BLOCK), " P ", "PFP", " P ", 'P', SilItems.PROCESSOR, 'F', BlockSilBlocks.EnumVariant.SILICON_FRAME);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(CONNECTOR), " C ", "WSW", 'W', SilItems.WIRE, 'C', SilItems.CIRCUIT_WIFI, 'S', ODItems.STONE);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(BLUE_GOO_BLOCK), "GGG", "GGG", "GGG", 'G', SilItems.BLUE_GOO);
        LMRecipes.INSTANCE.addShapelessRecipe(SilItems.BLUE_GOO.getStack(9), BLUE_GOO_BLOCK);

        for(BlockLamp.EnumLampColor color : BlockLamp.EnumLampColor.VALUES)
        {
            LMRecipes.INSTANCE.addRecipe(new ItemStack(LAMP, 1, color.ordinal()), "SBS", "RDR", "SBS", 'D', color.dyeName, 'S', ODItems.STONE, 'R', ODItems.REDSTONE, 'B', SilItems.ELEMITE_DUST.getStack(1));
        }
    }
}