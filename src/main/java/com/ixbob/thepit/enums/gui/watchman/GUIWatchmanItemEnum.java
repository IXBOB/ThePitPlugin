package com.ixbob.thepit.enums.gui.watchman;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public enum GUIWatchmanItemEnum {
    EXIT(Material.EMERALD, 1, 0, Utils.getInventoryIndex(2, 5),
            LangLoader.getString("watchman_item_id_0_name"), LangLoader.getStringList("watchman_item_id_0_lores"));

    private final Material material;
    private final int amount;
    private final int id;
    private final int index;
    private final String displayName;
    private final List<String> loreList;

    GUIWatchmanItemEnum(Material material, int amount, int id, int index, String displayName, List<String> loreList) {
        this.material = material;
        this.amount = amount;
        this.id = id;
        this.index = index;
        this.displayName = displayName;
        this.loreList = loreList;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

    public int getIndex() {
        return index;
    }

    public int getId() {
        return id;
    }

    public List<String> getLoreList() {
        return loreList;
    }

    public String getDisplayName() {
        return displayName;
    }
}
