package com.ixbob.thepit.enums.gui;

import com.ixbob.thepit.LangLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GUISystemItemEnum {
    DEFAULT_EMPTY(),
    DEFAULT_WALL(Material.STAINED_GLASS_PANE, 1, (short) 7, " "),
    TALENT_BUTTON_CANCEL(Material.STAINED_GLASS_PANE, 1, (short) 14, LangLoader.get("talent_system_item_cancel_name")),
    TALENT_BUTTON_APPLY(Material.STAINED_GLASS_PANE, 1, (short) 5, LangLoader.get("talent_system_item_equip_name")),
    TALENT_WALL_ALREADY_EQUIPPED(Material.STAINED_GLASS_PANE, 1, (short) 12, LangLoader.get("talent_item_has_equipped")),
    TALENT_WALL_EQUIP_GRID_LOCKED(Material.BARRIER, 1, (short) 0, LangLoader.get("talent_equip_grid_locked")),
    TALENT_WALL_LOCKED(Material.BEDROCK, 1, (short) 0, LangLoader.get("talent_item_locked"));

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
