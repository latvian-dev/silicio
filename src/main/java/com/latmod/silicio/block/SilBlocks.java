package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.lib.block.BlockVariantLookup;
import com.feed_the_beast.ftbl.lib.block.BlockWithVariants;
import com.feed_the_beast.ftbl.lib.item.ODItems;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.tile.TileConnector;
import com.latmod.silicio.tile.TileSocketBlock;
import com.latmod.silicio.tile.TileTurret;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class SilBlocks
{
    public static final BlockWithVariants<EnumSilBlocks> BLOCKS = new BlockWithVariants<EnumSilBlocks>(Material.ROCK, Silicio.INST.tab)
    {
        @Nonnull
        @Override
        public BlockVariantLookup<EnumSilBlocks> createMetaLookup()
        {
            return new BlockVariantLookup<>("variant", EnumSilBlocks.class, EnumSilBlocks.SILICON_BLOCK);
        }
    };

    public static final BlockSocketBlock SOCKET_BLOCK = new BlockSocketBlock();
    public static final BlockConnector CONNECTOR = new BlockConnector();
    public static final BlockBlueGoo BLUE_GOO_BLOCK = new BlockBlueGoo();
    public static final BlockTurret TURRET = new BlockTurret();

    public static void init()
    {
        Silicio.register("blocks", BLOCKS);
        Silicio.register("socket_block", SOCKET_BLOCK);
        Silicio.register("connector", CONNECTOR);
        Silicio.register("blue_goo", BLUE_GOO_BLOCK);
        Silicio.register("turret", TURRET);

        OreDictionary.registerOre(ODItems.GLASS, EnumSilBlocks.SILICON_GLASS.getStack(1));

        BLOCKS.registerTileEntities();

        GameRegistry.registerTileEntity(TileSocketBlock.class, "silicio.socket_block");
        GameRegistry.registerTileEntity(TileConnector.class, "silicio.connector");
        GameRegistry.registerTileEntity(TileTurret.class, "silicio.turret");
    }
}