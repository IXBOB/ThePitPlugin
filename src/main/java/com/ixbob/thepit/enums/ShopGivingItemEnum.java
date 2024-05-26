package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ShopGivingItemEnum {
    DIAMOND_HELMET(Material.DIAMOND_HELMET),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS);

    private final ItemStack itemStack;

    ShopGivingItemEnum(Material material){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (material == Material.DIAMOND_HELMET
                || material == Material.DIAMOND_CHESTPLATE
                || material == Material.DIAMOND_LEGGINGS
                || material == Material.DIAMOND_BOOTS){
            itemMeta.setUnbreakable(true);
        }
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }
}
