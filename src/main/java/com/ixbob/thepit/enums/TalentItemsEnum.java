package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TalentItemsEnum {
    HEALTH_BOOST(Material.REDSTONE, 0, 10, 0, Utils.getInventoryIndex(2,2), 1, LangLoader.get("talent_item_id_0_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_0_lore1"),
            LangLoader.get("talent_item_id_0_lore2"),
            LangLoader.get("talent_item_id_0_lore3"),
            LangLoader.get("talent_item_id_0_lore4"),
            LangLoader.get("talent_item_id_0_lore5"),
            LangLoader.get("talent_item_id_0_lore6"),
            LangLoader.get("talent_item_id_0_lore7")))),
    GOLDEN_CHOCOLATE(Material.GOLDEN_APPLE, 1, 10, 0, Utils.getInventoryIndex(2, 3), 1, LangLoader.get("talent_item_id_1_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_1_lore1"),
            LangLoader.get("talent_item_id_1_lore2"),
            LangLoader.get("talent_item_id_1_lore3"),
            LangLoader.get("talent_item_id_1_lore4"),
            LangLoader.get("talent_item_id_1_lore5"),
            LangLoader.get("talent_item_id_1_lore6"),
            LangLoader.get("talent_item_id_1_lore7"))));

    private final Material material;
    private final int id;
    private final int needLevel;
    private final int needPrestigeLevel;
    private final int index;
    private final int page;
    private final String displayName;
    private final List<String> loreList;

    TalentItemsEnum(Material material, int id, int needLevel, int needPrestigeLevel, int index, int page, String displayName, List<String> loreList) {
        this.material = material;
        this.id = id;
        this.needLevel = needLevel;
        this.needPrestigeLevel = needPrestigeLevel;
        this.index = index;
        this.page = page;
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

    public int getIndex() {
        return index;
    }

    public int getPage() {
        return page;
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
