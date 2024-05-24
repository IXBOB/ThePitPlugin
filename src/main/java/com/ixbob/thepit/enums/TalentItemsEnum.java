package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.TalentCalcuUtils;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TalentItemsEnum {
    HEALTH_BOOST(Material.REDSTONE, 0, 10, 0, 3, Utils.getInventoryIndex(2,2), 1,
            LangLoader.get("talent_item_id_0_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_0_lore1"),
            LangLoader.get("talent_item_id_0_lore2"),
            LangLoader.get("talent_item_id_0_lore3"),
            LangLoader.get("talent_item_id_0_lore4"),
            LangLoader.get("talent_item_id_0_lore5"),
            LangLoader.get("talent_item_id_0_lore6")))),
    GOLDEN_CHOCOLATE(Material.GOLDEN_APPLE, 1, 10, 0, 2, Utils.getInventoryIndex(2, 3), 1,
            LangLoader.get("talent_item_id_1_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_1_lore1"),
            LangLoader.get("talent_item_id_1_lore2"),
            LangLoader.get("talent_item_id_1_lore3"),
            LangLoader.get("talent_item_id_1_lore4"),
            LangLoader.get("talent_item_id_1_lore5"),
            LangLoader.get("talent_item_id_1_lore6"),
            LangLoader.get("talent_item_id_1_lore7")))),
    FISHERMAN(Material.FISHING_ROD, 2, 10, 0, 0, Utils.getInventoryIndex(2, 4), 1,
            LangLoader.get("talent_item_id_2_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_2_lore1"),
            LangLoader.get("talent_item_id_2_lore2"),
            LangLoader.get("talent_item_id_2_lore3"),
            LangLoader.get("talent_item_id_2_lore4"),
            LangLoader.get("talent_item_id_2_lore5"),
            LangLoader.get("talent_item_id_2_lore6")))),
    INFINITE_ARROWS(Material.ARROW, 3, 10, 0, 2, Utils.getInventoryIndex(2, 5), 1,
            LangLoader.get("talent_item_id_3_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_3_lore1"),
            LangLoader.get("talent_item_id_3_lore2"),
            LangLoader.get("talent_item_id_3_lore3"),
            LangLoader.get("talent_item_id_3_lore4"),
            LangLoader.get("talent_item_id_3_lore5"),
            LangLoader.get("talent_item_id_3_lore6")))),
    STRENGTH(Material.REDSTONE_BLOCK, 4, 15, 0, 3, Utils.getInventoryIndex(2, 6), 1,
            LangLoader.get("talent_item_id_4_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_4_lore1"),
            LangLoader.get("talent_item_id_4_lore2"),
            LangLoader.get("talent_item_id_4_lore3"),
            LangLoader.get("talent_item_id_4_lore4"),
            LangLoader.get("talent_item_id_4_lore5"),
            LangLoader.get("talent_item_id_4_lore6"))));

    private final Material material;
    private final int id;
    private final int needLevel;
    private final int needPrestigeLevel;
    private final int maxTalentLevel;
    private final int index;
    private final int page;
    private final String displayName;
    private final List<String> loreList;

    TalentItemsEnum(Material material, int id, int needLevel, int needPrestigeLevel, int maxTalentLevel, int index, int page, String displayName, List<String> loreList) {
        this.material = material;
        this.id = id;
        this.needLevel = needLevel;
        this.needPrestigeLevel = needPrestigeLevel;
        this.maxTalentLevel = maxTalentLevel;
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

    public int getMaxTalentLevel() {
        return maxTalentLevel;
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

    public double getNextLevelNeedCoinValue(int currentLevel) {
        return TalentCalcuUtils.getNextLevelAddCoinValue(id, currentLevel);
    }

    public float getAddPointValue(int level) {
        return TalentCalcuUtils.getAddPointValue(id, level);
    }

    public ItemStack getNamedItem(int level, boolean equipped, boolean hasReachedMaxLevel) {
        ItemStack itemStack = this.getItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        return defaultNameMethod(level, equipped, hasReachedMaxLevel, itemStack, itemMeta);
    }

    private ItemStack defaultNameMethod(int level, boolean equipped, boolean hasReachedMaxLevel, ItemStack itemStack, ItemMeta itemMeta) {
        ArrayList<String> copiedLoreList = new ArrayList<>(loreList);
        copiedLoreList.replaceAll(s -> s.replace("%Level%", String.valueOf(level))
                .replace("%NextLevelTip%", String.valueOf(hasReachedMaxLevel ? LangLoader.get("talent_item_next_level_tip_already_max") : LangLoader.get("talent_item_next_level_tip_need_coin")))
                .replace("%NextLevelNeedCoin%", String.valueOf(TalentUtils.getNextLevelNeedCoinAmount(this, level)))
                .replace("%AddPoint%", String.valueOf(this.getAddPointValue(level)))
                .replace("%ActionLeftClick%", String.valueOf(equipped ? LangLoader.get("talent_item_click_action_off") : LangLoader.get("talent_item_click_action_equip"))));
        itemMeta.setLore(copiedLoreList);
        itemMeta.setDisplayName(this.getDisplayName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}