package com.ixbob.thepit.enums.gui.shop;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum GUIShopItemEnum {
    DIAMOND_HELMET(Material.DIAMOND_HELMET, 1, 0, Utils.getInventoryIndex(2,2), 1, 300,
            LangLoader.get("shop_item_id_0_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("shop_item_id_0_lore1"),
            LangLoader.get("shop_item_id_0_lore2"),
            LangLoader.get("shop_item_id_0_lore3"),
            LangLoader.get("shop_item_id_0_lore4")))),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 1, 1, Utils.getInventoryIndex(2,3), 1, 450,
            LangLoader.get("shop_item_id_1_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("shop_item_id_1_lore1"),
            LangLoader.get("shop_item_id_1_lore2"),
            LangLoader.get("shop_item_id_1_lore3"),
            LangLoader.get("shop_item_id_1_lore4")))),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, 1, 2, Utils.getInventoryIndex(2,4), 1, 350,
            LangLoader.get("shop_item_id_2_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("shop_item_id_2_lore1"),
            LangLoader.get("shop_item_id_2_lore2"),
            LangLoader.get("shop_item_id_2_lore3"),
            LangLoader.get("shop_item_id_2_lore4")))),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, 1, 3, Utils.getInventoryIndex(2,5), 1, 260,
            LangLoader.get("shop_item_id_3_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("shop_item_id_3_lore1"),
            LangLoader.get("shop_item_id_3_lore2"),
            LangLoader.get("shop_item_id_3_lore3"),
            LangLoader.get("shop_item_id_3_lore4")))),
    OBSIDIAN(Material.OBSIDIAN, 8, 4, Utils.getInventoryIndex(2,6), 1, 50,
            LangLoader.get("shop_item_id_4_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("shop_item_id_4_lore1"),
            LangLoader.get("shop_item_id_4_lore2"),
            LangLoader.get("shop_item_id_4_lore3"),
            LangLoader.get("shop_item_id_4_lore4")))),
    DIAMOND_SWORD(Material.DIAMOND_SWORD, 1, 5, Utils.getInventoryIndex(2,7), 1, 150,
            LangLoader.get("shop_item_id_5_name"), new ArrayList<>(Arrays.asList(
            LangLoader.get("shop_item_id_5_lore1"),
            LangLoader.get("shop_item_id_5_lore2"),
            LangLoader.get("shop_item_id_5_lore3"),
            LangLoader.get("shop_item_id_5_lore4"))));

    private final Material material;
    private final int amount;
    private final int id;
    private final int index;
    private final int page;
    private final int needCoinAmount;
    private final String displayName;
    private final List<String> loreList;

    GUIShopItemEnum(Material material, int amount, int id, int index, int page, int needCoinAmount, String displayName, List<String> loreList) {
        this.material = material;
        this.amount = amount;
        this.id = id;
        this.index = index;
        this.page = page;
        this.needCoinAmount = needCoinAmount;
        this.displayName = displayName;
        this.loreList = loreList;
    }

    public ItemStack getItemStack() {
        return new ItemStack(material, amount);
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public int getPage() {
        return page;
    }

    public int getNeedCoinAmount() {
        return needCoinAmount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLoreList() {
        return loreList;
    }

    public ItemStack getNamedItem() {
        ItemStack itemStack = this.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        return defaultNameMethod(itemStack, itemMeta);
    }

    private ItemStack defaultNameMethod(ItemStack itemStack, ItemMeta itemMeta) {
        ArrayList<String> copiedLoreList = new ArrayList<>(loreList);
        itemMeta.setLore(copiedLoreList);
        itemMeta.setDisplayName(this.getDisplayName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}