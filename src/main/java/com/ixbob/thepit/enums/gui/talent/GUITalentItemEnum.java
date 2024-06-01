package com.ixbob.thepit.enums.gui.talent;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.event.OnPlayerEquippedTalentChangeListener;
import com.ixbob.thepit.util.TalentCalcuUtils;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum GUITalentItemEnum {
    //TODO: 参数第一项更改为ItemStack类型以方便添加特殊类型物品，将GOLDEN_CHOCOLATE显示图标更改为skull里面的金色巧克力
    HEALTH_BOOST(Material.REDSTONE, 0, null, 10, 0, 3,
            Utils.getInventoryIndex(2,2), 1,
            LangLoader.get("talent_item_id_0_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_0_lore1"),
            LangLoader.get("talent_item_id_0_lore2"),
            LangLoader.get("talent_item_id_0_lore3"),
            LangLoader.get("talent_item_id_0_lore4"),
            LangLoader.get("talent_item_id_0_lore5"),
            LangLoader.get("talent_item_id_0_lore6")))),
    GOLDEN_CHOCOLATE(Material.GOLDEN_APPLE, 1, null, 10, 0, 2,
            Utils.getInventoryIndex(2, 3), 1,
            LangLoader.get("talent_item_id_1_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_1_lore1"),
            LangLoader.get("talent_item_id_1_lore2"),
            LangLoader.get("talent_item_id_1_lore3"),
            LangLoader.get("talent_item_id_1_lore4"),
            LangLoader.get("talent_item_id_1_lore5"),
            LangLoader.get("talent_item_id_1_lore6"),
            LangLoader.get("talent_item_id_1_lore7")))),
    FISHERMAN(Material.FISHING_ROD, 2, null, 10, 0, 0,
            Utils.getInventoryIndex(2, 4), 1,
            LangLoader.get("talent_item_id_2_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_2_lore1"),
            LangLoader.get("talent_item_id_2_lore2"),
            LangLoader.get("talent_item_id_2_lore3"),
            LangLoader.get("talent_item_id_2_lore4"),
            LangLoader.get("talent_item_id_2_lore5"),
            LangLoader.get("talent_item_id_2_lore6")))),
    INFINITE_ARROWS(Material.ARROW, 3, null, 10, 0, 2,
            Utils.getInventoryIndex(2, 5), 1,
            LangLoader.get("talent_item_id_3_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_3_lore1"),
            LangLoader.get("talent_item_id_3_lore2"),
            LangLoader.get("talent_item_id_3_lore3"),
            LangLoader.get("talent_item_id_3_lore4"),
            LangLoader.get("talent_item_id_3_lore5"),
            LangLoader.get("talent_item_id_3_lore6")))),
    STRENGTH(Material.REDSTONE_BLOCK, 4, null, 15, 0, 3,
            Utils.getInventoryIndex(2, 6), 1,
            LangLoader.get("talent_item_id_4_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_4_lore1"),
            LangLoader.get("talent_item_id_4_lore2"),
            LangLoader.get("talent_item_id_4_lore3"),
            LangLoader.get("talent_item_id_4_lore4"),
            LangLoader.get("talent_item_id_4_lore5"),
            LangLoader.get("talent_item_id_4_lore6")))),
    SAFETY_FIRST(Material.CHAINMAIL_HELMET, 5, null, 15, 0, 0,
            Utils.getInventoryIndex(2, 7), 1,
            LangLoader.get("talent_item_id_5_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_5_lore1"),
            LangLoader.get("talent_item_id_5_lore2"),
            LangLoader.get("talent_item_id_5_lore3"),
            LangLoader.get("talent_item_id_5_lore4"),
            LangLoader.get("talent_item_id_5_lore5"),
            LangLoader.get("talent_item_id_5_lore6")))),
    MINER(Material.DIAMOND_PICKAXE, 6, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, 15, 0, 3,
            Utils.getInventoryIndex(2,8), 1,
            LangLoader.get("talent_item_id_6_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_6_lore1"),
            LangLoader.get("talent_item_id_6_lore2"),
            LangLoader.get("talent_item_id_6_lore3"),
            LangLoader.get("talent_item_id_6_lore4"),
            LangLoader.get("talent_item_id_6_lore5"),
            LangLoader.get("talent_item_id_6_lore6"),
            LangLoader.get("talent_item_id_6_lore7"),
            LangLoader.get("talent_item_id_6_lore8"),
            LangLoader.get("talent_item_id_6_lore9")))),
    STREAM(Material.GOLD_INGOT, 7, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, 20, 0, 3,
            Utils.getInventoryIndex(3,2), 1,
            LangLoader.get("talent_item_id_7_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_7_lore1"),
            LangLoader.get("talent_item_id_7_lore2"),
            LangLoader.get("talent_item_id_7_lore3"),
            LangLoader.get("talent_item_id_7_lore4"),
            LangLoader.get("talent_item_id_7_lore5"),
            LangLoader.get("talent_item_id_7_lore6"),
            LangLoader.get("talent_item_id_7_lore7")))),
    FLEXIBLE_TACTICS(Material.STRING, 8, null, 20, 0, 3,
            Utils.getInventoryIndex(3,3), 1,
            LangLoader.get("talent_item_id_8_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("talent_item_id_8_lore1"),
            LangLoader.get("talent_item_id_8_lore2"),
            LangLoader.get("talent_item_id_8_lore3"),
            LangLoader.get("talent_item_id_8_lore4"),
            LangLoader.get("talent_item_id_8_lore5"),
            LangLoader.get("talent_item_id_8_lore6"),
            LangLoader.get("talent_item_id_8_lore7"))));

    /**
     * {@link TalentCalcuUtils}
     * {@link OnPlayerEquippedTalentChangeListener#applyEquipChangeResult()}
     */

    private final Material material;
    private final int id;
    private final ItemFlag[] itemFlags;
    private final int needLevel;
    private final int needPrestigeLevel;
    private final int maxTalentLevel;
    private final int index;
    private final int page;
    private final String displayName;
    private final List<String> loreList;

    GUITalentItemEnum(Material material, int id, ItemFlag[] itemFlags, int needLevel, int needPrestigeLevel, int maxTalentLevel, int index, int page, String displayName, List<String> loreList) {
        this.material = material;
        this.id = id;
        this.itemFlags = itemFlags;
        this.needLevel = needLevel;
        this.needPrestigeLevel = needPrestigeLevel;
        this.maxTalentLevel = maxTalentLevel;
        this.index = index;
        this.page = page;
        this.displayName = displayName;
        this.loreList = loreList;
    }

    public ItemFlag[] getItemFlags() {
        return itemFlags;
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
        ItemStack itemStack = new ItemStack(material, amount);
        if (itemFlags != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            for (ItemFlag itemFlag : itemFlags) {
                itemMeta.addItemFlags(itemFlag);
            }
            itemStack.setItemMeta(itemMeta);
        }
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