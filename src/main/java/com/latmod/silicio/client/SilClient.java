package com.latmod.silicio.client;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.latmod.silicio.SilCommon;
import com.latmod.silicio.tile.TileAntimatter;
import com.latmod.silicio.tile.TileTurret;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
    @Override
    public void preInit()
    {
        FTBLibClient.addTileRenderer(TileAntimatter.class, new RenderAntimatter());
        FTBLibClient.addTileRenderer(TileTurret.class, new RenderTurret());
        //OBJLoader.instance.addDomain(Silicio.mod.getID());
    }
}