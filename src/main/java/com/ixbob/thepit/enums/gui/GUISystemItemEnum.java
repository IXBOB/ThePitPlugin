package com.ixbob.thepit.enums.gui;

import com.ixbob.thepit.LangLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum GUISystemItemEnum {
    DEFAULT_EMPTY(),
    DEFAULT_WALL(Material.GRAY_STAINED_GLASS_PANE, 1, " "),
    TALENT_BUTTON_CANCEL(Material.RED_STAINED_GLASS_PANE, 1, LangLoader.getString("talent_system_item_cancel_name")),
    TALENT_BUTTON_APPLY(Material.LIME_STAINED_GLASS_PANE, 1, LangLoader.getString("talent_system_item_equip_name")),
    TALENT_WALL_ALREADY_EQUIPPED(Material.MAGENTA_STAINED_GLASS_PANE, 1, LangLoader.getString("talent_item_has_equipped")),
    TALENT_WALL_EQUIP_GRID_LOCKED(Material.BARRIER, 1, LangLoader.getString("talent_equip_grid_locked")),
    TALENT_WALL_LOCKED(Material.BEDROCK, 1, LangLoader.getString("talent_item_locked"));

    private final ItemStack itemStack;

    GUISystemItemEnum(Material material, int amount, String displayName) {
        this.itemStack = new ItemStack(material, amount);
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

    public ItemStack getItemStack(ArrayList<String> formatStrings) {
        ItemStack itemStack1 = new ItemStack(itemStack);
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        itemMeta1.setDisplayName(String.format(itemMeta1.getDisplayName(), formatStrings.toArray()));
        itemStack1.setItemMeta(itemMeta1);
        return itemStack1;
    }
}
