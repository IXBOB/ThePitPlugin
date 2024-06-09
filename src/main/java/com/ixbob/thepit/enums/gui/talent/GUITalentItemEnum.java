package com.ixbob.thepit.enums.gui.talent;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.enums.CustomSkullEnum;
import com.ixbob.thepit.event.OnPlayerEquippedTalentChangeListener;
import com.ixbob.thepit.util.TalentCalcuUtils;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum GUITalentItemEnum {
    HEALTH_BOOST(new ItemStack(Material.REDSTONE), 0, null, 10, 0, 3,
            Utils.getInventoryIndex(2,2), 1,
            LangLoader.getString("talent_item_id_0_name"), LangLoader.getStringList("talent_item_id_0_lores")),
    GOLDEN_CHOCOLATE(CustomSkullEnum.GOLDEN_CHOCOLATE.getItemStack(), 1, null, 10, 0, 2,
            Utils.getInventoryIndex(2, 3), 1,
            LangLoader.getString("talent_item_id_1_name"), LangLoader.getStringList("talent_item_id_1_lores")),
    FISHERMAN(new ItemStack(Material.FISHING_ROD), 2, null, 10, 0, 0,
            Utils.getInventoryIndex(2, 4), 1,
            LangLoader.getString("talent_item_id_2_name"), LangLoader.getStringList("talent_item_id_2_lores")),
    INFINITE_ARROWS(new ItemStack(Material.ARROW), 3, null, 10, 0, 2,
            Utils.getInventoryIndex(2, 5), 1,
            LangLoader.getString("talent_item_id_3_name"), LangLoader.getStringList("talent_item_id_3_lores")),
    STRENGTH(new ItemStack(Material.REDSTONE_BLOCK), 4, null, 15, 0, 3,
            Utils.getInventoryIndex(2, 6), 1,
            LangLoader.getString("talent_item_id_4_name"), LangLoader.getStringList("talent_item_id_4_lores")),
    SAFETY_FIRST(new ItemStack(Material.CHAINMAIL_HELMET), 5, null, 15, 0, 0,
            Utils.getInventoryIndex(2, 7), 1,
            LangLoader.getString("talent_item_id_5_name"), LangLoader.getStringList("talent_item_id_5_lores")),
    MINER(new ItemStack(Material.DIAMOND_PICKAXE), 6, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, 15, 0, 3,
            Utils.getInventoryIndex(2,8), 1,
            LangLoader.getString("talent_item_id_6_name"), LangLoader.getStringList("talent_item_id_6_lores")),
    STREAM(new ItemStack(Material.GOLD_INGOT), 7, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, 20, 0, 3,
            Utils.getInventoryIndex(3,2), 1,
            LangLoader.getString("talent_item_id_7_name"), LangLoader.getStringList("talent_item_id_7_lores")),
    FLEXIBLE_TACTICS(new ItemStack(Material.STRING), 8, null, 20, 0, 3,
            Utils.getInventoryIndex(3,3), 1,
            LangLoader.getString("talent_item_id_8_name"), LangLoader.getStringList("talent_item_id_8_lores")),;

    /**
     * {@link TalentCalcuUtils}
     * {@link OnPlayerEquippedTalentChangeListener#applyEquipChangeResult()}
     */

    private final ItemStack itemStack;
    private final int id;
    private final ItemFlag[] itemFlags;
    private final int needLevel;
    private final int needPrestigeLevel;
    private final int maxTalentLevel;
    private final int index;
    private final int page;
    private final String displayName;
    private final List<String> loreList;

    GUITalentItemEnum(ItemStack itemStack, int id, ItemFlag[] itemFlags, int needLevel, int needPrestigeLevel, int maxTalentLevel, int index, int page, String displayName, List<String> loreList) {
        this.itemStack = itemStack;
        this.id = id;
        this.itemFlags = itemFlags;
        this.needLevel = needLevel;
        this.needPrestigeLevel = needPrestigeLevel;
        this.maxTalentLevel = maxTalentLevel;
        this.index = index;
        this.page = page;
        this.displayName = displayName;
        this.loreList = loreList;
        if (itemFlags != null) {
            ItemMeta itemMeta = this.itemStack.getItemMeta();
            for (ItemFlag itemFlag : itemFlags) {
                itemMeta.addItemFlags(itemFlag);
            }
            this.itemStack.setItemMeta(itemMeta);
        }
    }

    public ItemFlag[] getItemFlags() {
        return itemFlags;
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

    public ItemStack getItemStack() {
        return itemStack;
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
        ItemStack itemStack = this.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        return defaultNameMethod(level, equipped, hasReachedMaxLevel, itemStack, itemMeta);
    }

    private ItemStack defaultNameMethod(int level, boolean equipped, boolean hasReachedMaxLevel, ItemStack itemStack, ItemMeta itemMeta) {
        ArrayList<String> copiedLoreList = new ArrayList<>(loreList);
        copiedLoreList.replaceAll(s -> s.replace("%Level%", String.valueOf(level))
                .replace("%NextLevelTip%", String.valueOf(hasReachedMaxLevel ? LangLoader.getString("talent_item_next_level_tip_already_max") : LangLoader.getString("talent_item_next_level_tip_need_coin")))
                .replace("%NextLevelNeedCoin%", String.valueOf(TalentUtils.getNextLevelNeedCoinAmount(this, level)))
                .replace("%AddPoint%", String.valueOf(this.getAddPointValue(level)))
                .replace("%ActionLeftClick%", String.valueOf(equipped ? LangLoader.getString("talent_item_click_action_off") : LangLoader.getString("talent_item_click_action_equip"))));
        itemMeta.setLore(copiedLoreList);
        itemMeta.setDisplayName(this.getDisplayName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}