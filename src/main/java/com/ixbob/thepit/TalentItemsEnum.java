package com.ixbob.thepit;

import com.ixbob.thepit.handler.config.LangLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TalentItemsEnum {
    HEALTH_BOOST(Material.REDSTONE, LangLoader.get("talent_item_add_health_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_add_health_lore1"),
            LangLoader.get("talent_item_add_health_lore2"),
            LangLoader.get("talent_item_add_health_lore3"),
            LangLoader.get("talent_item_add_health_lore4"),
            LangLoader.get("talent_item_add_health_lore5"),
            LangLoader.get("talent_item_add_health_lore6"),
            LangLoader.get("talent_item_add_health_lore7"))));

    private final Material material;
    private final String displayName;
    private final List<String> loreList;

    TalentItemsEnum(Material material, String displayName, List<String> loreList) {
        this.material = material;
        this.displayName = displayName;
        this.loreList = loreList;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItemStack(int amount, int level) {
        this.initLoreFormatWithLevel(level);
        return new ItemStack(material, amount);
    }

    private void initLoreFormatWithLevel(int level) {
        if (this == HEALTH_BOOST) {
            loreList.replaceAll(s -> s.replace("%level%", String.valueOf(level))
                    .replace("%nextLevelNeedCoin%", String.valueOf((level + 1) * 500))
                    .replace("%addMaxHealth%", String.valueOf(level * 2)));
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLoreList() {
        return loreList;
    }
}
