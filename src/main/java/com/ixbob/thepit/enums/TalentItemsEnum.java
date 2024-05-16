package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TalentItemsEnum {
    HEALTH_BOOST(Material.REDSTONE, 0, 10, 0, LangLoader.get("talent_item_add_health_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_add_health_lore1"),
            LangLoader.get("talent_item_add_health_lore2"),
            LangLoader.get("talent_item_add_health_lore3"),
            LangLoader.get("talent_item_add_health_lore4"),
            LangLoader.get("talent_item_add_health_lore5"),
            LangLoader.get("talent_item_add_health_lore6"),
            LangLoader.get("talent_item_add_health_lore7"))));

    private final Material material;
    private final int id;
    private final int needLevel;
    private final int needPrestigeLevel;
    private final String displayName;
    private final List<String> loreList;

    TalentItemsEnum(Material material, int id, int needLevel, int needPrestigeLevel, String displayName, List<String> loreList) {
        this.material = material;
        this.id = id;
        this.needLevel = needLevel;
        this.needPrestigeLevel = needPrestigeLevel;
        this.displayName = displayName;
        this.loreList = loreList;
    }

    public Material getMaterial() {
        return material;
    }

    public int getNeedLevel() {
        return needLevel;
    }

    public int getNeedPrestigeLevel() {
        return needPrestigeLevel;
    }

    public ItemStack getItemStack(int amount) {
        return new ItemStack(material, amount);
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLoreList() {
        return new ArrayList<>(loreList);
    }

    public int getId() {
        return id;
    }
}
