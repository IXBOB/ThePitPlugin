package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum TalentGivingItemEnum {
    DEFAULT_FISHING_ROD(Material.FISHING_ROD, null, null, null),
    DEFAULT_CHAINMAIL_HELMET(Material.CHAINMAIL_HELMET, null, null, null),
    DIAMOND_PICKAXE_EFFICIENCY_4(Material.DIAMOND_PICKAXE, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, Enchantment.DIG_SPEED, 4),
    DEFAULT_COBBLESTONE(Material.COBBLESTONE, null, null, null);

    private final ItemStack itemStack;

    TalentGivingItemEnum(Material material, ItemFlag[] itemFlags, Enchantment enchantment, Integer level){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        if (itemFlags != null) {
            itemMeta.addItemFlags(itemFlags);
        }
        if (enchantment != null) {
            itemMeta.addEnchant(enchantment, level, true);
        }
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }

    public ItemStack getItemStack(int amount) {
        return new ItemStack(this.itemStack.getType(), amount);
    }
}
