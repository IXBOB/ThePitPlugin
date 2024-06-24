package com.ixbob.thepit.enums.gui.shop;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.GUIUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum GUIShopItemEnum {
    DIAMOND_HELMET(Material.DIAMOND_HELMET, 1, 0, GUIUtils.getInvIndex(2,2), 1, 300,
            LangLoader.getString("shop_item_id_0_name"), LangLoader.getStringList("shop_item_id_0_lores")),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 1, 1, GUIUtils.getInvIndex(2,3), 1, 450,
            LangLoader.getString("shop_item_id_1_name"), LangLoader.getStringList("shop_item_id_1_lores")),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, 1, 2, GUIUtils.getInvIndex(2,4), 1, 350,
            LangLoader.getString("shop_item_id_2_name"), LangLoader.getStringList("shop_item_id_2_lores")),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, 1, 3, GUIUtils.getInvIndex(2,5), 1, 260,
            LangLoader.getString("shop_item_id_3_name"), LangLoader.getStringList("shop_item_id_3_lores")),
    OBSIDIAN(Material.OBSIDIAN, 8, 4, GUIUtils.getInvIndex(2,6), 1, 50,
            LangLoader.getString("shop_item_id_4_name"), LangLoader.getStringList("shop_item_id_4_lores")),
    DIAMOND_SWORD(Material.DIAMOND_SWORD, 1, 5, GUIUtils.getInvIndex(2,7), 1, 150,
            LangLoader.getString("shop_item_id_5_name"), LangLoader.getStringList("shop_item_id_5_lores")),;

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
