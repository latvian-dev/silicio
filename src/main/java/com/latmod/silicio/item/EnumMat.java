package com.latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.IMaterial;
import com.latmod.lib.EnumNameMap;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public enum EnumMat implements IMaterial
{
    BLUE_GOO(3),
    LASER_LENS(4),
    XSUIT_PLATE(5),
    ANTIMATTER(6),
    OPAL(7),
    //
    ELEMITE_INGOT(10),
    ELEMITE_NUGGET(11),
    ELEMITE_DUST(12),
    //
    WIRE(20),
    RESISTOR(21),
    CAPACITOR(22),
    DIODE(23),
    TRANSISTOR(24),
    CHIP(25),
    PROCESSOR(26),
    CIRCUIT(27),
    CIRCUIT_WIFI(28),
    LED_RED(29),
    LED_GREEN(30),
    LED_BLUE(31),
    LED_RGB(32),
    LED_MATRIX(33),
    //
    MODULE_EMPTY(60),
    MODULE_INPUT(61),
    MODULE_OUTPUT(62),
    MODULE_LOGIC(63);

    private final String name;
    private final int meta;

    EnumMat(int id)
    {
        name = EnumNameMap.createName(this);
        meta = id;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Item getItem()
    {
        return SilItems.MAT;
    }

    @Override
    public int getMetadata()
    {
        return meta;
    }
}
