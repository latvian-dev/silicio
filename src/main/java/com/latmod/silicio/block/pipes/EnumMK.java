package com.latmod.silicio.block.pipes;

import com.feed_the_beast.ftbl.lib.EnumNameMap;
import net.minecraft.util.IStringSerializable;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public enum EnumMK implements IStringSerializable
{
    MK1,
    MK2,
    MK3;

    private final String name = EnumNameMap.createName(this);

    @Override
    public String getName()
    {
        return name;
    }
}
