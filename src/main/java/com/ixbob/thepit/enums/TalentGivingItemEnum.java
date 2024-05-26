package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum TalentGivingItemEnum {
    DEFAULT_FISHING_ROD(Material.FISHING_ROD),
    DEFAULT_CHAINMAIL_HELMET(Material.CHAINMAIL_HELMET);

    private final ItemStack itemStack;

    TalentGivingItemEnum(Material material){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }
}
