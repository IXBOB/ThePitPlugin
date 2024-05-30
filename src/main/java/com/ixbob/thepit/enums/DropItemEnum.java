package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum DropItemEnum {
    IRON_HELMET(new ItemStack(Material.IRON_HELMET)),
    IRON_CHESTPLATE(new ItemStack(Material.IRON_CHESTPLATE)),
    IRON_LEGGINGS(new ItemStack(Material.IRON_LEGGINGS)),
    IRON_BOOTS(new ItemStack(Material.IRON_BOOTS));

    private ItemStack itemStack;

    DropItemEnum(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void apply(Player player) {
        ItemStack itemHelmet = player.getInventory().getHelmet();
        ItemStack itemChestplate = player.getInventory().getChestplate();
        ItemStack itemLeggings = player.getInventory().getLeggings();
        ItemStack itemBoots = player.getInventory().getBoots();
        if (this == IRON_HELMET && itemHelmet == null) {
            player.getInventory().setHelmet(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        else if (this == IRON_CHESTPLATE && itemChestplate.getType() == Material.CHAINMAIL_CHESTPLATE) {
            player.getInventory().setChestplate(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        else if (this == IRON_LEGGINGS && itemLeggings.getType() == Material.CHAINMAIL_LEGGINGS) {
            player.getInventory().setLeggings(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        else if (this == IRON_BOOTS && itemLeggings.getType() == null) {
            player.getInventory().setBoots(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
    }

    public static void init() {
        for (DropItemEnum item : DropItemEnum.values()) {
            ItemStack itemStack1 = item.getItemStack();
            ItemMeta itemMeta = itemStack1.getItemMeta();
            if (item == DropItemEnum.IRON_HELMET
                    || item == DropItemEnum.IRON_CHESTPLATE
                    || item == DropItemEnum.IRON_LEGGINGS
                    || item == DropItemEnum.IRON_BOOTS) {
                itemMeta.setUnbreakable(true);
            }
            itemStack1.setItemMeta(itemMeta);
            item.setItemStack(itemStack1);
        }
    }
}
