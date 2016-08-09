package com.latmod.silicio.item.xsuit;

import com.google.common.collect.Multimap;
import com.latmod.silicio.item.ItemSil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * Created by LatvianModder on 09.08.2016.
 */
public class ItemXSuitBelt extends ItemSil
{
    static final UUID MODIFIER = UUID.fromString("476d5a78-15dc-475c-851b-83586979ed39");

    public ItemXSuitBelt()
    {
        setMaxStackSize(1);
        setMaxDamage(256);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity)
    {
        return armorType == EntityEquipmentSlot.CHEST;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return "silicio:textures/items/xsuit/armor.png";
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if(slot == EntityEquipmentSlot.CHEST)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(MODIFIER, "Armor modifier", 1D, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getAttributeUnlocalizedName(), new AttributeModifier(MODIFIER, "Armor toughness", 10D, 0));
        }

        return multimap;
    }
}
