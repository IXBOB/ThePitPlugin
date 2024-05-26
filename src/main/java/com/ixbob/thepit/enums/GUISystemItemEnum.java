package com.ixbob.thepit.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GUISystemItemEnum {
    DEFAULT_EMPTY(),
    DEFAULT_WALL(Material.STAINED_GLASS_PANE, 1, (short) 7, " ");
    //TODO: 添加其他重复的GUI物品，墙，空气...之类的

    private final ItemStack itemStack;

    GUISystemItemEnum(Material material, int amount, short extraData, String displayName) {
        this.itemStack = new ItemStack(material, amount, extraData);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
    }

    GUISystemItemEnum() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
