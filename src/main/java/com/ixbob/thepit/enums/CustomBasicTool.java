package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomBasicTool {
    BASIC_STONE_SWORD(new ItemStack(Material.STONE_SWORD)),
    BASIC_BOW(new ItemStack(Material.BOW)),
    BASIC_CHAINMAIL_CHESTPLATE(new ItemStack(Material.CHAINMAIL_CHESTPLATE)),
    BASIC_CHAINMAIL_LEGGINGS(new ItemStack(Material.CHAINMAIL_LEGGINGS));

    private ItemStack itemStack;

    CustomBasicTool(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static void init() {
        for (CustomBasicTool tool : CustomBasicTool.values()) {
            ItemStack itemStack1 = tool.getItemStack();
            ItemMeta itemMeta = itemStack1.getItemMeta();
            if (tool == CustomBasicTool.BASIC_STONE_SWORD
                    || tool == CustomBasicTool.BASIC_BOW
                    || tool == CustomBasicTool.BASIC_CHAINMAIL_CHESTPLATE
                    || tool == CustomBasicTool.BASIC_CHAINMAIL_LEGGINGS) {
                itemMeta.setUnbreakable(true);
            }
            itemStack1.setItemMeta(itemMeta);
            tool.setItemStack(itemStack1);
        }
    }
}
