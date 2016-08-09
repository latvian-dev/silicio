package com.latmod.silicio.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 09.08.2016.
 */
public class ItemXSuit extends ItemArmor
{
    public ItemXSuit(EntityEquipmentSlot slot)
    {
        super(ArmorMaterial.IRON, 0, slot);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return slot == EntityEquipmentSlot.LEGS ? "silicio:textures/items/xsuit/xsuit_1.png" : "silicio:textures/items/xsuit/xsuit_0.png";
    }
}
