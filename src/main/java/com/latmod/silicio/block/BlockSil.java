package com.latmod.silicio.block;

import com.feed_the_beast.ftbl.api.block.BlockLM;
import com.latmod.silicio.Silicio;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockSil extends BlockLM
{
    public BlockSil(Material m)
    {
        super(m);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return Silicio.INST.tab;
    }
}