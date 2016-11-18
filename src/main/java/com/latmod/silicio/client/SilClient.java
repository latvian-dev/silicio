package com.latmod.silicio.client;

import com.latmod.silicio.SilCommon;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.item.SilItems;
import com.latmod.silicio.tile.TileLamp;
import com.latmod.silicio.tile.TileTurret;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class SilClient extends SilCommon
{
    @Override
    public void preInit()
    {
        super.preInit();

        //OBJLoader.instance.addDomain(Silicio.mod.getID());

        SilBlocks.BLOCKS.registerModels();
        SilBlocks.SOCKET_BLOCK.registerDefaultModel();
        SilBlocks.CONNECTOR.registerDefaultModel();
        SilBlocks.BLUE_GOO_BLOCK.registerDefaultModel();
        SilBlocks.TURRET.registerDefaultModel();

        ClientRegistry.bindTileEntitySpecialRenderer(TileTurret.class, new RenderTurret());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new RenderLamp());

        SilItems.MAT.loadModels();
        SilItems.ID_CARD.registerDefaultModel();
        SilItems.MULTITOOL.registerDefaultModel();
        //SilItems.XSUIT_BELT.registerDefaultModel();
        //SilItems.XSUIT_VISOR.registerDefaultModel();

        SilItems.MODULE.registerModels();
    }
}