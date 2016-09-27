package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.lib.item.ItemLM;
import com.latmod.silicio.Silicio;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class ItemSil extends ItemLM
{
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab()
    {
        return Silicio.INST.tab;
    }
}