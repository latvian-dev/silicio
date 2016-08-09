package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.ItemMaterialsLM;
import com.latmod.silicio.Silicio;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 06.02.2016.
 */
public class ItemSilMaterials extends ItemMaterialsLM
{
    public ItemSilMaterials()
    {
        setCreativeTab(Silicio.tab);
    }

    @Override
    @Nonnull
    public String getFolder()
    {
        return "materials";
    }
}