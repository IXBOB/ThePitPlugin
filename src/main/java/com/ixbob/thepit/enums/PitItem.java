package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum PitItem {
    GOLDEN_CHOCOLATE(CustomSkull.GOLDEN_CHOCOLATE.getItemStack(), LangLoader.get("talent_item_id_1_name"));

    private final ItemStack itemStack;
    private final String name;

    PitItem(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
        this.name = name;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public String getName() {
        return  this.name;
    }
}
