package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum DropItems {
    IRON_HELMET(new ItemStack(Material.IRON_HELMET)),
    IRON_CHESTPLATE(new ItemStack(Material.IRON_CHESTPLATE)),
    IRON_LEGGINGS(new ItemStack(Material.IRON_LEGGINGS)),
    IRON_BOOTS(new ItemStack(Material.IRON_BOOTS));

    private ItemStack itemStack;

    DropItems(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void apply(Player player) {
        if (this == IRON_HELMET) {
            player.getInventory().setHelmet(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        else if (this == IRON_CHESTPLATE) {
            player.getInventory().setChestplate(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        else if (this == IRON_LEGGINGS) {
            player.getInventory().setLeggings(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        else if (this == IRON_BOOTS) {
            player.getInventory().setBoots(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
    }

    public static void init() {
        for (DropItems item : DropItems.values()) {
            ItemStack itemStack1 = item.getItemStack();
            ItemMeta itemMeta = itemStack1.getItemMeta();
            if (item == DropItems.IRON_HELMET
                    || item == DropItems.IRON_CHESTPLATE
                    || item == DropItems.IRON_LEGGINGS
                    || item == DropItems.IRON_BOOTS) {
                itemMeta.setUnbreakable(true);
            }
            itemStack1.setItemMeta(itemMeta);
            item.setItemStack(itemStack1);
        }
    }
}
