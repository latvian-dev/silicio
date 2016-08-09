package com.latmod.silicio.client;

import com.latmod.silicio.SilCommon;
import com.latmod.silicio.block.SilBlocks;
import com.latmod.silicio.item.SilItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SilClient extends SilCommon
{
    @Override
    public void preInit()
    {
        //OBJLoader.instance.addDomain(Silicio.mod.getID());
        SilBlocks.initModels();
        SilItems.initModels();
    }
}